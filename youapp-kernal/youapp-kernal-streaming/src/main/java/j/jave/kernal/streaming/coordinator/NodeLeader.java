package j.jave.kernal.streaming.coordinator;

import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.coordinator.rpc.leader.ExecutingWorker;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingServiceFactory;
import j.jave.kernal.streaming.netty.client.KryoChannelExecutorPool;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

@SuppressWarnings({"serial","rawtypes"})
public class NodeLeader implements Serializable{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(NodeLeader.class);
	
	private final KryoChannelExecutorPool channelExecutorPool=new KryoChannelExecutorPool();
	
	private final JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	private final Map conf;

	private final LeaderNodeMeta leaderNodeMeta;
	
	private final int id;
	
	private final String name;
	
	private static String basePath=CoordinatorPaths.BASE_PATH;
	
	private final ZookeeperExecutor executor;
	
	private LeaderLatch leaderLatch;

	private WorkflowMaster workflowMaster;
	
	private TrackingService trackingService;
	
	private ExecutorService loggingExecutorService=null;
	
	private ExecutorService zooKeeperExecutorService=null;
	
	private SimpleHttpNioChannelServer server;
	
	private static NodeLeader NODE_SELECTOR;
	
	/**
	 * never used in other case
	 */
	private final Object lockLeaderLatch=new Object();
	
	/**
	 * start up a node leader... (as a node of the leader cluster)
	 * @param name identified node name
	 * @param executor zookeeper connection
	 * @param conf the leader node config
	 * @return
	 */
	public synchronized static NodeLeader startup(ZookeeperExecutor executor,Map conf){
		NodeLeader nodeSelector=NODE_SELECTOR;
		if(nodeSelector==null){
			nodeSelector=new NodeLeader(conf, executor);
			NODE_SELECTOR=nodeSelector;
		}
		return NODE_SELECTOR;
	}
	
	/**
	 * get the runtime / available node leader...
	 * @return
	 */
	public static NodeLeader runtime(){
		JAssert.isNotNull(NODE_SELECTOR,"leader is not ready, retry later");
		return NODE_SELECTOR;
	}
	
	private NodeLeader(Map conf,ZookeeperExecutor executor) {
		this.conf=conf;
		this.leaderNodeMeta=new LeaderNodeMetaGetter(JConfiguration.get(), conf).nodeMeta();
		this.id=leaderNodeMeta.getId();
		this.name=leaderNodeMeta.getName();
		this.executor = executor;
		SingleMonitor.startup(executor);
		trackingService=TrackingServiceFactory.build(conf);
		registerAsFollowerInZookeeper();
		
		loggingExecutorService=Executors.newFixedThreadPool(leaderNodeMeta.getLogThreadCount()
				,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"{logging(leader node)}");
			}
		});
		zooKeeperExecutorService=Executors.newFixedThreadPool(leaderNodeMeta.getZkThreadCount()
				,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"{zookeeper watcher(leader node)}");
			}
		});
		
		leaderLatch=new LeaderLatch(executor.backend(),
				leaderPath());
		leaderLatch.addListener(new LeaderLatchListener() {
			
			@Override
			public void notLeader() {
				logInfo("(Thread)+"+Thread.currentThread().getName()+" lose worker-schedule leadership .... ");
				if(workflowMaster==null) return;
				closeWorkflowMaster();
			}
			
			@Override
			public void isLeader() {
				logInfo("(Thread)+"+Thread.currentThread().getName()+" is worker-schedule leadership .... ");
				try {
					createMasterMeta();
				} catch (Exception e) {
					logError(e);
					exitIfCloseWorkflowMasterNeed();
				}
			}
		}, Executors.newFixedThreadPool(1,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "node-leader-listener(leader)...");
			}
		}));
		
		Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "node-leader-selector(leader)");
			}
		}).execute(new Runnable() {
			@Override
			public void run() {
				startLeader();
			}
		});
	}

	String workflowPath(final Workflow workflow){
		return workflow==null?basePath:(basePath+"/"+workflow.getName());
	}
	
	String pluginWorkersPath(final Workflow workflow){
		return workflow.pluginWorkersPath();
	}
	
	String pluginWorkerPath(int workerId,Workflow workflow){
		String pluginWorkersPath= pluginWorkersPath(workflow);
		return pluginWorkersPath+"/worker-"+String.valueOf(workerId);
	}
	
	String pluginWorkerInstancePath(int workerId,Instance instance){
		return pluginWorkerPath(workerId,instance.getWorkflow())+"/instance/"+instance.getSequence();
	}
	
	String pluginWorkerProcessorPath(int workerId,Workflow workflow){
		return pluginWorkerPath(workerId, workflow)+"/processor";
	}
	
	String instancePath(String path,Instance instance){
		return basePath+"/instance/"+instance.getWorkflow().getName()+"/"+instance.getSequence()+path;
	}
	
	String workflowTrigger(final Workflow workflow){
		String triggerPath=basePath+"/workflow-trigger";
		return workflow==null?triggerPath:(triggerPath+"/"+workflow.getName());
	}
	
	public static String workflowAddPath(){
		return basePath+"/workflow-meta-collection";
	}
	
	public static String leaderPath(){
		return basePath+"/leader-latch";
	}
	
	public static String basePath(){
		return basePath;
	}
	
	public static String leaderFollowerRegisterPath(){
		return basePath+"/leader-as-follower-host";
	}
	
	public static String leaderIsLeaderRegisterPath(){
		return basePath+"/leader-is-leader-host";
	}
	
	public static String simpleTrackingPath(){
		return basePath+"-tasks-tracking/sequence";
	}
	

	private synchronized void createMasterMeta() throws Exception{
		logInfo("(Thread)+"+Thread.currentThread().getName()+" got worker-schedule leadership .... ");
		closeWorkflowMaster();
		leaderNodeMeta.setLeader(true);
		workflowMaster=new WorkflowMaster(this,leaderNodeMeta);
		registerLeaderIsLeaderInZookeeper();
		registerRPCInZookeeper();
		startServer();
	}
	
	
	
	private void exitIfCloseWorkflowMasterNeed() {
		if(workflowMaster!=null) {
			try{
				workflowMaster.close();
				workflowMaster=null;
			}catch (Exception e) {
				logError(e);
			}
		}
		try{
			synchronized (this) {
				wait(10000);
			}
		}catch (Exception e1) {
			logError(e1);
		}
		System.exit(-1);
	}

	private void closeWorkflowMaster() {
		if(workflowMaster!=null) {
			try{
				workflowMaster.close();
				workflowMaster=null;
			}catch (Exception e) {
				logError(e);
				try{
					wait(10000);
				}catch (Exception e1) {
					logError(e1);
				}
				System.exit(-1);
			}
		}
	}
	
	/**
	 * node leader  code
	 * @throws Exception
	 */
	private void startServer() throws Exception{
		LeaderNodeMeta nodeMeta=workflowMaster.getLeaderNodeMeta();
		server =
				new SimpleHttpNioChannelServer(nodeMeta.getPort());
		server.start();
	}
	
	/**
	 * node leader code
	 */
	private void registerRPCInZookeeper(){
		LeaderNodeMeta leaderNodeMeta=workflowMaster.getLeaderNodeMeta();
		String rpcNode=JConfiguration.get().getString(ConfigNames.STREAMING_LEADER_RPC_HOST_ZNODE);
		String data=leaderNodeMeta.getHost()
				+":"
				+leaderNodeMeta.getPort();
		if(executor.exists(rpcNode)){
			executor.setPath(rpcNode, data);
		}else{
			executor.createPath(rpcNode, data);
		}
	}
	
	private String realLeaderFollowerPath() {
		return leaderFollowerRegisterPath()
														+"/"+id
														+"-"+leaderNodeMeta.getHost()
														+"-"+leaderNodeMeta.getPid();
	}
	
	/**
	 * node leader code
	 * @return
	 */
	private synchronized LeaderNodeMeta registerAsFollowerInZookeeper(){
        byte[] msg=
        		SerializerUtils.serialize(serializerFactory, leaderNodeMeta);
        String realLeaderFollowerPath=realLeaderFollowerPath();
		if(!executor.exists(realLeaderFollowerPath)){
			executor.createPath(realLeaderFollowerPath, msg);
			executor.createEphSequencePath(realLeaderFollowerPath+"/t-");
		}
		else{
			executor.setPath(realLeaderFollowerPath, msg);
			if(executor.getChildren(realLeaderFollowerPath).isEmpty()){
				executor.createEphSequencePath(realLeaderFollowerPath+"/t-");
			}
		}
		return leaderNodeMeta;
	}
	
	/**
	 * node leader code
	 * @return
	 */
	private synchronized LeaderNodeMeta registerLeaderIsLeaderInZookeeper(){
        byte[] msg=
        		SerializerUtils.serialize(serializerFactory, leaderNodeMeta);
        String realLeaderIsLeaderPath=leaderIsLeaderRegisterPath();
		if(!executor.exists(realLeaderIsLeaderPath)){
			executor.createPath(realLeaderIsLeaderPath, msg);
		}else{
			executor.setPath(realLeaderIsLeaderPath, msg);
		}
		String realLeaderFollowerPath=realLeaderFollowerPath();
		if(executor.exists(realLeaderFollowerPath)){
			executor.deletePath(realLeaderFollowerPath);
		}
		return leaderNodeMeta;
	}
	
	
	
	
	
	public void startWorkflow(String name,Map<String, Object> conf){
		JAssert.isNotEmpty(name);
		workflowMaster.startWorkflow(name, conf);
	}
	
	/**
	 * attempt to latch leader.
	 */
	private void startLeader(){
		
		try {
			logInfo("(Thread)+"+Thread.currentThread().getName()+" attempt to join worker-schedule leadership .... ");
			leaderLatch.start();
			while(true){
				try{
					leaderLatch.await();
					break;
				}catch (InterruptedException e) {
					continue;
				}catch (EOFException e) {
					logError(e);
					exitIfCloseWorkflowMasterNeed();
				}catch (Exception e) {
					logError(e);
					exitIfCloseWorkflowMasterNeed();
				}
			}
			
//			createMasterMeta();  // avoid do again and again , see isLeader listener... LeaderLatchListener
			
			while(true){
				try{
					synchronized (lockLeaderLatch) {
						lockLeaderLatch.wait();
					}
				}catch (InterruptedException e) {
				}
			}
			
		} catch (Exception e) {
			logError(e);
			exitIfCloseWorkflowMasterNeed();
		}finally {
			try {
				if(leaderLatch.hasLeadership()){
					leaderLatch.close();
				}
			} catch (IOException e) {
				logError(e);
				exitIfCloseWorkflowMasterNeed();
			}
		}
	}
	
	
	
	
	
	public boolean sendHeartbeats(ExecutingWorker executingWorker){
		return workflowMaster.sendHeartbeats(executingWorker);
	}
	
	public String addWorkflowMeta(WorkflowMeta workflowMeta){
		return workflowMaster.addWorkflowMeta(workflowMeta);
	}
	
	public String removeWorkflowMeta(String workflowName){
		return workflowMaster.removeWorkflowMeta(workflowName);
	}
	
	
	ZookeeperExecutor getExecutor() {
		return executor;
	}
	
	public Map getConf() {
		return conf;
	}

	void logError(Throwable e) {
		LOGGER.error(getMessage(e.getMessage()), e);
	}
	
	void logInfo(String msg) {
		LOGGER.info(getMessage(msg));
	}
	
	private String getMessage(String msg){
		return String.format("---leader-name[%s]--%s---", new Object[]{name,msg});
	}
	
	public int getId() {
		return id;
	}
	
	public WorkflowMaster workflowMaster() {
		return workflowMaster;
	}
	
	ExecutorService zooKeeperExecutorService() {
		return zooKeeperExecutorService;
	}
	
	ExecutorService getLoggingExecutorService() {
		return loggingExecutorService;
	}
	
	TrackingService getTrackingService() {
		return trackingService;
	}
	
	KryoChannelExecutorPool getChannelExecutorPool() {
		return channelExecutorPool;
	}
}
