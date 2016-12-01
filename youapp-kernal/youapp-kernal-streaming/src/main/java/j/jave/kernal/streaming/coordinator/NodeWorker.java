package j.jave.kernal.streaming.coordinator;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.zookeeper.CreateMode;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.coordinator.command.WorkerTemporary;
import j.jave.kernal.streaming.coordinator.rpc.worker.IWorkerService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingServiceFactory;
import j.jave.kernal.streaming.netty.client.KryoChannelExecutor;
import j.jave.kernal.streaming.netty.client.SimpleInterfaceImplUtil;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooNode;
import j.jave.kernal.streaming.zookeeper.ZooNodeCallback;
import j.jave.kernal.streaming.zookeeper.ZooNodeChildrenCallback;


@SuppressWarnings("serial")
public class NodeWorker implements Serializable {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(NodeWorker.class);
	
	private JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	private Map conf;
	
	private String logPrefix;
	
	private int id;
	
	public String name;
	
	private WorkflowMeta workflowMeta;
	
	private ZookeeperExecutor executor;

	private LeaderLatch processorLeaderLatch;
	
	private WorkerTemporary workerTemporary;
	
	private ProcessorMaster processorMaster;
	
	private Processor processor;
	
	private TrackingService trackingService;
	
	private ExecutorService executorService=Executors.newFixedThreadPool(3);
	
	private SimpleHttpNioChannelServer server;
	
	public NodeWorker(int id, String workerName,WorkflowMeta workflowMeta,ZookeeperExecutor executor,Map conf) {
		this.id = id;
		this.name = workerName;
		this.logPrefix="(worker["+workerName+"])+"+id+" ";
		this.workflowMeta=workflowMeta;
		this.conf=conf;
		trackingService=TrackingServiceFactory.build(conf);
		this.executor=executor;
		startWorker();
	}

	private String pluginWorkerPath(){
		String pluginWorkersPath= CoordinatorPaths.BASE_PATH
				+"/pluginWorkers/"+workflowMeta.getName()+"-workers";
		return pluginWorkersPath+"/worker-"+String.valueOf(id);
	}
	
	private String pluginWorkerInstancePath(long sequence){
		return pluginWorkerPath()+"/instance/"+sequence;
	}
	
	private String pluginWorkerProcessorPath(){
		return pluginWorkerPath()+"/processor";
	}
	
	private void startServer(WorkerNodeMeta workerNodeMeta) throws Exception{
		server =
				new SimpleHttpNioChannelServer(workerNodeMeta.getPort());
		server.start();
	}
	
	/**
	 * start this worker
	 */
	private void startWorker(){
		final String pluginWorkerPath=pluginWorkerPath();
		SingleMonitor singleMonitor=SingleMonitor.get(
				CoordinatorPaths.BASE_PATH
				+"/worker-register-sync-lock/"+workflowMeta.getName()+"/"+id); 
		try{
			singleMonitor.acquire();
			if(!executor.exists(pluginWorkerPath)){
//				WorkerPathVal workerPathVal=new WorkerPathVal();
//				workerPathVal.setId(id);
//				workerPathVal.setTime(new Date().getTime());
				executor.createPath(pluginWorkerPath,
//						KryoUtils.serialize(serializerFactory, workerPathVal),
						CreateMode.PERSISTENT);
			}
			WorkerNodeMetaGetter metaGetter=new WorkerNodeMetaGetter(JConfiguration.get());
			WorkerNodeMeta workerNodeMeta=metaGetter.nodeMeta();
			Integer port=(Integer) conf.get(WorkerConfigNames.WORKER_NETTY_PORT);
			if(port==null){
				port=8080;
			}
			workerNodeMeta.setPort(port);
			String workerProcessorPath=pluginWorkerProcessorPath()
															+"/"+workerNodeMeta.getHost()
															+"-"+workerNodeMeta.getPid();
			executor.createPath(workerProcessorPath, SerializerUtils.serialize(serializerFactory, workerNodeMeta));
			executor.createEphSequencePath(workerProcessorPath+"/temp-");
			
			//start server 
			startServer(workerNodeMeta);
			
			Processor processor=new Processor();
			processor.setTempPath(workerProcessorPath);
			processor.setWorkerNodeMeta(workerNodeMeta);
			this.processor=processor;
		}catch (Exception e) {
			logError(e);
		}finally{
			try {
				singleMonitor.release();
			} catch (Exception e) {
				logError(e);
			}
		}
		
		// processor leader latch...
		processorLeaderLatch=new LeaderLatch(executor.backend(),
				processorLeaderPath());
		processorLeaderLatch.addListener(new LeaderLatchListener() {
			
			@Override
			public void notLeader() {
				if(processorMaster==null) return;
				System.out.println(logPrefix+"(Thread)+"+Thread.currentThread().getName()+" lose worker-processor leadership .... ");
				try {
					processorMaster.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				processorMaster=null;
			}
			
			@Override
			public void isLeader() {
				System.out.println(logPrefix+"(Thread)+"+Thread.currentThread().getName()+" is worker-processor leadership .... ");
				createMasterMeta();
			}
		}, Executors.newFixedThreadPool(1));
		Executors.newFixedThreadPool(1, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, pluginWorkerPath+"{watch children(leader)}");
			}
		}).execute(new Runnable() {
			
			@Override
			public void run() {
				try{
					processorLeaderLatch.start();
					processorLeaderLatch.await();
					createMasterMeta();

					while(true){
						try{
							synchronized (this) {
								wait();
							}
						}catch (InterruptedException e) {
						}
					}
					
				}catch (Exception e) {
					logError(e);
				}finally {
					try {
						processorLeaderLatch.close();
					} catch (Exception e) {
						logError(e);
					}
				}
			}
		});
	}
	
	/**
	 * leader code
	 */
	private synchronized void createMasterMeta(){
		if(processorMaster!=null) return;
		processorMaster=new ProcessorMaster();
		attachPluginWorkerPathWatcher(pluginWorkerPath());
	}
	
	/**
	 * add watcher on the worker path, expect the znode is trigger per workflow instance,
	 * leader  code
	 * @param pluginWorkerPath
	 */
	private void attachPluginWorkerPathWatcher(final String pluginWorkerPath){
		
		System.out.println(logPrefix+"  add wahter on : "+pluginWorkerPath);
		executor.watchPath(pluginWorkerPath, new ZooNodeCallback () {
			@Override
			public void call(ZooNode node) {
				try{
					final WorkerPathVal workerPathVal=
							SerializerUtils.deserialize(serializerFactory, node.getDataAsPossible(executor), 
									WorkerPathVal.class);
					String workerExecutingPath=pluginWorkerInstancePath(workerPathVal.getSequence());
					executor.createPath(workerExecutingPath, 
							SerializerUtils.serialize(serializerFactory, workerPathVal));
					
					WorkerExecutingPathVal workerExecutingPathVal=new WorkerExecutingPathVal();
					workerExecutingPathVal.setWorkerExecutingPath(workerExecutingPath);
					workerExecutingPathVal.setWorkerPathVal(workerPathVal);
					// add watcher on the worker executing path for every workflow instance
					final PathChildrenCache cache= 
					executor.watchChildrenPath(workerExecutingPath, new ZooNodeChildrenCallback() {
						@Override
						public void call(List<ZooNode> nodes) {
							if(nodes.isEmpty()){
								try{
									WorkerPathVal workerPathVal= workerPathVal();
									complete(workerPathVal.getInstancePath());
								}finally{
									try {
										processorMaster.closeInstance(workerExecutingPath);
									} catch (Exception e) {
										LOGGER.error(e.getMessage(), e);
									}
								}
							}
						}
					}, Executors.newFixedThreadPool(1, new ThreadFactory() {
						@Override
						public Thread newThread(Runnable r) {
							return new Thread(r, pluginWorkerPath+"{cache:watch children}");
						}
					}),PathChildrenCacheEvent.Type.CHILD_REMOVED);
					processorMaster.addProcessorsWather(workerExecutingPath, cache);
					// notify sub-processors of worker
					String processorPath=pluginWorkerProcessorPath();
					List<String> chls=executor.getChildren(processorPath);
					for(String chPath:chls){
						String relProcessorPath=processorPath+"/"+chPath;
						if(!executor.getChildren(relProcessorPath).isEmpty()){
							WorkerNodeMeta workerNodeMeta=
								SerializerUtils.deserialize(serializerFactory, 
										executor.getPath(relProcessorPath), WorkerNodeMeta.class);
							IWorkerService workerService=
									SimpleInterfaceImplUtil.syncProxy(IWorkerService.class,
											new KryoChannelExecutor(workerNodeMeta.getHost(),
													workerNodeMeta.getPort()));
							workerService.notifyWorkers(workerExecutingPathVal);
						}
					}
				}catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		},Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, pluginWorkerPath());
			}
		}));
		
	}
	
	private void complete(final String path){
		final InstanceNodeVal instanceNodeVal=
				SerializerUtils.deserialize(serializerFactory, executor.getPath(path),
						InstanceNodeVal.class);
		instanceNodeVal.setStatus(NodeStatus.COMPLETE);
		executor.setPath(path, 
				SerializerUtils.serialize(serializerFactory, instanceNodeVal));
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				WorkTracking workTracking=
						workTracking(instanceNodeVal.getId(), instanceNodeVal.getSequence(), path,
								NodeStatus.COMPLETE);
				trackingService.track(workTracking);
			}
		});
	}
	
	
	private WorkTracking workTracking(int workerId,long instanceId,String path,NodeStatus status){
		WorkTracking workTracking=new WorkTracking();
		workTracking.setWorkerId(String.valueOf(workerId));
		workTracking.setInstancePath(path);
		workTracking.setStatus(status);
		Date date=new Date();
		workTracking.setRecordTime(date.getTime());
		workTracking.setRecordTimeStr(JDateUtils.formatWithSeconds(date));
		workTracking.setWorkerName(this.name);
		workTracking.setId(JUniqueUtils.unique());
		workTracking.setOffset(-1);
		workTracking.setHashKey(String.valueOf(instanceId));
		return workTracking;
	}
	
	private WorkerPathVal workerPathVal(){
		byte[] bytes= executor.getPath(pluginWorkerPath());
		return SerializerUtils.deserialize(serializerFactory, bytes, WorkerPathVal.class);
	}
	
	public int getId() {
		return id;
	}
	
	String processorLeaderPath(){
		return CoordinatorPaths.BASE_PATH
				+"/worker-processor-leader-latch/"+workflowMeta.getName()+"/"+id;
	}
	
	public void acquire() throws Exception{
		
		while(true){
			try{
				synchronized (this) {
					System.out.println(logPrefix+" go to wait .... ");
					wait();
					System.out.println(logPrefix+" waked up ... ");
					break;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean acquire(long time, TimeUnit unit) throws Exception{
		int count=0;
		while(true){
			try{
				if(count>3){
					break;
				}
				synchronized (this) {
					try{
						wait(unit.toMillis(time));
						return true;
					}catch (Exception e) {
						count++;
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new TimeoutException(" worker cannot be scheduled.");
	}
	
	private void setWorkerTemporary(WorkerTemporary workerTemporary) {
		this.workerTemporary = workerTemporary;
	}
	
	public Map<String, Object> getWorkflowConf(){
		return workerTemporary.getWorkerPathVal().getConf();
	}
	
	public void release() throws Exception{
		release(null);
	}
	
	public void release(Throwable t) throws Exception{
		System.out.println(logPrefix+" delete temp path : "+workerTemporary.getTempPath());
		executor.deletePath(workerTemporary.getTempPath());
	}
	
	private synchronized void wakeup(){
		notifyAll();
	}
	
	/**
	 * wake up the real executing processor/thread
	 * @param workerExecutingPathVal
	 */
	public void wakeup(WorkerExecutingPathVal workerExecutingPathVal){
		String workerExecutingPath=workerExecutingPathVal.getWorkerExecutingPath();
		if(executor.getChildren(workerExecutingPath).isEmpty()){  // avoid wake up multiple times
			final String tempPath=executor.createEphSequencePath(
					workerExecutingPath+"/temp-");
			WorkerPathVal workerPathVal=workerExecutingPathVal.getWorkerPathVal();
			WorkerTemporary workerTemporary=new WorkerTemporary();
			workerTemporary.setTempPath(tempPath);
			workerTemporary.setWorkerPathVal(workerPathVal);
			setWorkerTemporary(workerTemporary);
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					WorkTracking workTracking=
							workTracking(workerPathVal.getId(), 
									workerPathVal.getSequence(), tempPath,
									NodeStatus.PROCESSING);
					trackingService.track(workTracking);
				}
			});
			wakeup();
		}
	}
	
	private void logError(Exception e) {
		LOGGER.error(getMessage(e.getMessage()), e);
	}
	
	private String getMessage(String msg){
		return String.format("---worker-id[%s]--%s---", new Object[]{id,msg});
	}
	
}
