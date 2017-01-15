package j.jave.kernal.streaming.coordinator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.zookeeper.CreateMode;

import j.jave.kernal.streaming.Util;
import j.jave.kernal.streaming.coordinator.command.WorkerTemporary;
import j.jave.kernal.streaming.coordinator.rpc.leader.ExecutingWorker;
import j.jave.kernal.streaming.coordinator.rpc.leader.IWorkflowService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingServiceFactory;
import j.jave.kernal.streaming.netty.client.SimpleInterfaceImplUtil;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.serializer.JSerializerFactory;
import me.bunny.kernel.jave.serializer.SerializerUtils;
import me.bunny.kernel.jave.utils.JDateUtils;
import me.bunny.kernel.jave.utils.JUniqueUtils;


@SuppressWarnings("serial")
public class NodeWorker implements Serializable {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(NodeWorker.class);
	
	private final String sequence=new Random().nextInt(100)+"-"+ 
			JUniqueUtils.sequence();
	
	private JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	private final WorkerNodeMeta workerNodeMeta;
	
	private final Map conf;
	
	private final int id;
	
	public final String name;
	
	/**
	 * what workflow the worker is servicing for
	 */
	private WorkflowMeta workflowMeta;
	
	private ZookeeperExecutor executor;
	
	/**
	 * which instance of the workfkow at this time 
	 */
	private WorkerTemporary workerTemporary;
	
	/**
	 * the processor/thread meta data info , never be changed from born
	 */
	private Processor processor;
	
	private TrackingService trackingService;
	
	private ExecutorService loggingExecutorService;
	
	private ExecutorService zooKeeperExecutorService;
	
	private SimpleHttpNioChannelServer server;
	
	private IWorkflowService workflowService;
	
	private ScheduledExecutorService heartBeatExecutorService;
	
	public NodeWorker(WorkflowMeta workflowMeta,Map conf,ZookeeperExecutor executor) {
		this.workerNodeMeta=new WorkerNodeMetaGetter(JConfiguration.get(), conf).nodeMeta();
		this.id = workerNodeMeta.getId();
		this.name = workerNodeMeta.getName();
		this.workflowMeta=workflowMeta;
		this.conf=conf;
		trackingService=TrackingServiceFactory.build(conf);
		workflowService=SimpleInterfaceImplUtil.asyncProxy(IWorkflowService.class);
		this.executor=executor;
		heartBeatExecutorService=Executors.newScheduledThreadPool(1,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"{worker["+getId()+"]-heart-beat-to-leader");
			}
		});
		
		this.loggingExecutorService=Executors.newFixedThreadPool(workerNodeMeta.getLogThreadCount(),
				new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"{worker["+getId()+"]-logging(node["+id+"] worker...)}");
			}
		});
		this.zooKeeperExecutorService=Executors.newFixedThreadPool(workerNodeMeta.getZkThreadCount(),
				new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"{zookeeper watcher(node["+id+"] worker...)}");
			}
		});
		startWorker();
	}

	private String pluginWorkerPath(){
		String pluginWorkersPath= CoordinatorPaths.BASE_PATH
				+"/pluginWorkers/"+workflowMeta.getName()+"-workers";
		return pluginWorkersPath+"/worker-"+String.valueOf(id);
	}
	
//	private String pluginWorkerInstancePath(long sequence){
//		return pluginWorkerPath()+"/instance/"+sequence;
//	}
	
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
				RestrictLockPath.workerLockPath(workflowMeta.getName(), id)); 
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
			String workerProcessorPath=realProcessorPath(workerNodeMeta);
			executor.createPath(workerProcessorPath, SerializerUtils.serialize(serializerFactory, workerNodeMeta));
			executor.createEphSequencePath(workerProcessorPath+"/temp-");
			
			//start server 
			startServer(workerNodeMeta);
			
			Processor processor=new Processor();
			processor.setNodePath(workerProcessorPath);
			processor.setWorkerNodeMeta(workerNodeMeta);
			this.processor=processor;
			scheduleHeartBeats();
		}catch (Exception e) {
			logError(e);
		}finally{
			try {
				singleMonitor.release();
			} catch (Exception e) {
				logError(e);
			}
		}

	}

	/**
	 * send heart beat to node leader , include instance/workflow meta...
	 */
	private void scheduleHeartBeats() {
		heartBeatExecutorService.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try{
					WorkerTemporary workerTemporary=getWorkerTemporary();
					if(workerTemporary!=null){
						ExecutingWorker executingWorker=new ExecutingWorker();
						WorkerExecutingPathVal workerExecutingPathVal=workerTemporary.getWorkerExecutingPathVal();
						WorkerPathVal workerPathVal=workerExecutingPathVal.getWorkerPathVal();
						executingWorker.setSequence(workerPathVal.getSequence());
						executingWorker.setWorkerId(id);
						executingWorker.setWorkflowName(workflowMeta.getName());
						executingWorker.setWorkerInstancePath(workerTemporary.getTempPath());
						executingWorker.setWorkflowInstancePath(workerPathVal.getInstancePath());
						Date date=new Date();
						executingWorker.setTime(date.getTime());
						executingWorker.setTimeStr(JDateUtils.formatWithMSeconds(date));
						workflowService.sendHeartbeats(executingWorker);
					}
				}catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}, 0, workerNodeMeta.getHeartBeatTimeMs(),TimeUnit.MILLISECONDS);
	}

	private String realProcessorPath(WorkerNodeMeta workerNodeMeta) {
		return pluginWorkerProcessorPath()
														+"/"+workerNodeMeta.getHost()
														+"-"+workerNodeMeta.getPid()
														+"-sequence["+sequence+"]";
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
	
//	private WorkerPathVal workerPathVal(){
//		byte[] bytes= executor.getPath(pluginWorkerPath());
//		return SerializerUtils.deserialize(serializerFactory, bytes, WorkerPathVal.class);
//	}
	
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
					logInfo(JDateUtils.formatWithSeconds(new Date())+" go to wait .... ");
					wait();
					logInfo(JDateUtils.formatWithSeconds(new Date())+" waked up ... ");
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
		return workerTemporary.getWorkerExecutingPathVal().getWorkerPathVal().getConf();
	}
	
	public void release() throws Exception{
		release(null);
	}
	
	public void release(Throwable t) throws Exception{
		try{
			logInfo(" delete temp path : "+workerTemporary.getTempPath());
			if(t!=null){
				executor.createPath(workerTemporary.getWorkerExecutingPathVal().getWorkerExecutingErrorPath()+"/e-"
						,SerializerUtils.serialize(serializerFactory, Util.getMsg(t))
				,CreateMode.EPHEMERAL_SEQUENTIAL);
			}
			executor.deletePath(workerTemporary.getTempPath());
			workerTemporary=null;
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}finally{
			
		}
		
	}
	
	private synchronized void wakeup(){
		logInfo(" ok , let's wakeup itself (notifyAll) ");
		notifyAll();
	}
	
	private boolean hasDone(WorkerExecutingPathVal workerExecutingPathVal){
		String workerExecutingPath=workerExecutingPathVal.getWorkerExecutingPath();
		List<String> paths=executor.getChildren(workerExecutingPath);
		boolean done=false;
		for (String string : paths) {
			if(string.indexOf(sequence)!=-1){
				done=true;
				break;
			}
		}
		return done;
	}
	
	/**
	 * wake up the real executing processor/thread
	 * @param workerExecutingPathVal
	 */
	public void wakeup(WorkerExecutingPathVal workerExecutingPathVal){
		logInfo(" ready to wakeup itself ");
		String workerExecutingPath=workerExecutingPathVal.getWorkerExecutingPath();
		boolean done=hasDone(workerExecutingPathVal);
		if(!done){  // avoid wake up multiple times
			final String tempPath=executor.createEphSequencePath(
					workerExecutingPath+"/temp-");
			final WorkerPathVal workerPathVal=workerExecutingPathVal.getWorkerPathVal();
			WorkerTemporary workerTemporary=new WorkerTemporary();
			workerTemporary.setTempPath(tempPath);
			workerTemporary.setWorkerExecutingPathVal(workerExecutingPathVal);
			setWorkerTemporary(workerTemporary);
			loggingExecutorService.execute(new Runnable() {
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
		}else{
			logInfo(" already done , skip ");
		}
	}
	
	private void logError(Exception e) {
		LOGGER.error(getMessage(e.getMessage()), e);
	}
	
	private String getMessage(String msg){
		return String.format("---worker-id[%s]--%s---", new Object[]{id,msg});
	}
	
	private void logInfo(String msg) {
		LOGGER.info(getMessage(msg));
	}
	
	private WorkerTemporary getWorkerTemporary() {
		return workerTemporary;
	}
	
	/**
	 * the indication path by current worker processor/thread , never changed
	 * @return
	 */
	public String getProcessorPath() {
		return processor.getNodePath();
	}
	
}
