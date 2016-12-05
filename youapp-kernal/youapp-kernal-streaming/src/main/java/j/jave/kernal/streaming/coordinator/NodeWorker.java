package j.jave.kernal.streaming.coordinator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.zookeeper.CreateMode;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.Util;
import j.jave.kernal.streaming.coordinator.command.WorkerTemporary;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingServiceFactory;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;


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
	
	private ExecutorService loggingExecutorService=null;
	
	private ExecutorService zooKeeperExecutorService=null;
	
	private SimpleHttpNioChannelServer server;
	
	public NodeWorker(WorkflowMeta workflowMeta,Map conf,ZookeeperExecutor executor) {
		this.workerNodeMeta=new WorkerNodeMetaGetter(JConfiguration.get(), conf).nodeMeta();
		this.id = workerNodeMeta.getId();
		this.name = workerNodeMeta.getName();
		this.workflowMeta=workflowMeta;
		this.conf=conf;
		trackingService=TrackingServiceFactory.build(conf);
		this.executor=executor;
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
			String workerProcessorPath=realProcessorPath(workerNodeMeta);
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
		logInfo(" delete temp path : "+workerTemporary.getTempPath());
		executor.deletePath(workerTemporary.getTempPath());
		if(t!=null){
			executor.createPath(workerTemporary.getWorkerExecutingPathVal().getWorkerExecutingErrorPath()+"/e-"
					,SerializerUtils.serialize(serializerFactory, Util.getMsg(t))
			,CreateMode.EPHEMERAL_SEQUENTIAL);
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
			WorkerPathVal workerPathVal=workerExecutingPathVal.getWorkerPathVal();
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
}
