package j.jave.kernal.streaming.coordinator;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import com.google.common.collect.Maps;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.coordinator.WorkerMaster.InstaneCheck;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand.WorkflowCommandModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowCompleteCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowCompleteModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowErrorCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowErrorModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowRetryCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowRetryModel;
import j.jave.kernal.streaming.coordinator.rpc.leader.IWorkflowService;
import j.jave.kernal.streaming.coordinator.rpc.worker.IWorkerService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingServiceFactory;
import j.jave.kernal.streaming.netty.client.KryoChannelExecutorPool;
import j.jave.kernal.streaming.netty.client.SimpleInterfaceImplUtil;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooNode;
import j.jave.kernal.streaming.zookeeper.ZooNodeCallback;
import j.jave.kernal.streaming.zookeeper.ZooNodeChildrenCallback;

@SuppressWarnings({"serial","rawtypes"})
public class NodeLeader implements Serializable{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(NodeLeader.class);
	
	private static KryoChannelExecutorPool executorPool=new KryoChannelExecutorPool();
	
	private JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	private final Map conf;

	private final LeaderNodeMeta leaderNodeMeta;
	
	private final int id;
	
	private final String name;
	
	private static String basePath=CoordinatorPaths.BASE_PATH;
	
	private final ZookeeperExecutor executor;
	
	private LeaderLatch leaderLatch;
	
	private DistAtomicLong atomicLong;

	private WorkflowMaster workflowMaster;
	
	private TrackingService trackingService;
	
	private ExecutorService loggingExecutorService=null;
	
	private ExecutorService zooKeeperExecutorService=null;
	
	private SimpleHttpNioChannelServer server;
	
	public static abstract class CacheType{
		private static final String CHILD="patch_child";
	}
	
	private static NodeLeader NODE_SELECTOR;
	
	
	private final Object sync=new Object();
	
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
		this.atomicLong=new DistAtomicLong(executor);
		trackingService=TrackingServiceFactory.build(conf);
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
				if(workflowMaster==null) return;
				logInfo("(Thread)+"+Thread.currentThread().getName()+" lose worker-schedule leadership .... ");
				try {
					workflowMaster.close();
				} catch (IOException e) {
					logError(e);
				}
				workflowMaster=null;
			}
			
			@Override
			public void isLeader() {
				logInfo("(Thread)+"+Thread.currentThread().getName()+" is worker-schedule leadership .... ");
				try {
					createMasterMeta();
				} catch (Exception e) {
					logError(e);
					throw new IllegalStateException(e);
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
		return basePath+"/pluginWorkers/"+workflow.getName()+"-workers";
	}
	
	String pluginWorkerPath(int workerId,Instance instance){
		String pluginWorkersPath= pluginWorkersPath(instance.getWorkflow());
		return pluginWorkersPath+"/worker-"+String.valueOf(workerId);
	}
	
	String pluginWorkerInstancePath(int workerId,Instance instance){
		return pluginWorkerPath(workerId,instance)+"/instance/"+instance.getSequence();
	}
	
	String pluginWorkerProcessorPath(int workerId,Instance instance){
		return pluginWorkerPath(workerId, instance)+"/processor";
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
	
	public static String leaderRegisterPath(){
		return basePath+"/leader-host";
	}
	
	public static String simpleTrackingPath(){
		return basePath+"-tasks-tracking/sequence";
	}
	
	private long getSequence(){
		return atomicLong.getSequence();
	}
	
	/**
	 * add workflows to current master instance, including attaching any watcher on worker registered path.
	 * @param workflowMeta
	 */
	private synchronized void addWorkflow(WorkflowMeta workflowMeta){
//		if(!workflowMaster.existsWorkflow(workflowMeta.getName())){
			Workflow workflow=workflowMaster.getWorkflow(workflowMeta.getName());
			if(workflow==null){
				workflow=new Workflow(workflowMeta.getName());
				workflow.setNodeData(workflowMeta.getNodeData());
				workflow.setWorkflowMeta(workflowMeta);
				workflowMaster.addWorkflow(workflow);
				attachWorkersPathWatcher(workflow);
			}
			else{
				workflow.setNodeData(workflowMeta.getNodeData());
			}
//		}
	}
	
	/**
	 * close any IO related to current instance node.
	 * @param sequence
	 * @param path
	 * @param cacheType
	 */
	private void close(long sequence,String path,String cacheType){
		if(path!=null){
			try {
				InstanceNode instanceNode=workflowMaster.getInstance(sequence).getInstanceNodes().get(path);
				if(CacheType.CHILD.equals(cacheType)){
					instanceNode.getPathChildrenCache().close();
				}
			} catch (IOException e) {
				logError(e);
			}
		}
	}
	
	/**
	 * create all instance path of nodes in the workflow
	 *  when a workflow is triggered.
	 * @param c
	 * @param instance
	 */
	private void createInstancePath(NodeData c,Instance instance){
		if(c!=null){
			String instancePath=instancePath(c.getPath(),instance);
			// set initialization information of node path on zookeeper node
			InstanceNodeVal instanceNodeVal=new InstanceNodeVal();
			instanceNodeVal.setId(c.getId());
			instanceNodeVal.setParallel(c.getParallel());
			instanceNodeVal.setStatus(NodeStatus.ONLINE);
			instanceNodeVal.setSequence(instance.getSequence());
			instanceNodeVal.setTime(new Date().getTime());
			executor.createPath(instancePath,
					SerializerUtils.serialize(serializerFactory, instanceNodeVal));
			
			InstanceNode instanceNode=new InstanceNode();
			instanceNode.setSequence(instance.getSequence());
			instanceNode.setPath(instancePath);
			instanceNode.setInstanceNodeVal(instanceNodeVal);
			instanceNode.setId(c.getId());
			instanceNode.setNodeData(c);
			instance.addInstanceNode(instancePath, instanceNode);
			
			if(c.hasChildren()){
				//is;s logical path  / virtual path 
				if(JStringUtils.isNullOrEmpty(instance.getRootPath())){
					instance.setRootPath(instancePath);
				}
				instance.addChildPathWatcherPath(instancePath);
				for(NodeData data:c.getNodes()){
					createInstancePath(data, instance);
				}
			}else{
				// it;s worker path.
				instance.addWorkerPath(instancePath);
			}
		}
	}
	
	/**
	 * complete current virtual node , also close some IO 
	 * @param instance
	 * @param _path
	 */
	private void completeVirtual(final Instance instance, final String _path
			,final NodeStatus completeStatus) {
		// the virtual node is completed, set flag to complete
		c(_path,completeStatus);
		//close instance children node watcher, release IO
		close(instance.getSequence(), _path, CacheType.CHILD);
	}
	
	/**
	 * is;s call  when the logical/virtual node is completed.
	 * @param path
	 */
	private void c(final String path,final NodeStatus completeStatus){
		final InstanceNodeVal instanceNodeVal=
				SerializerUtils.deserialize(serializerFactory, executor.getPath(path), InstanceNodeVal.class);
		instanceNodeVal.setStatus(completeStatus);
		instanceNodeVal.setTime(new Date().getTime());
		executor.setPath(path, SerializerUtils.serialize(serializerFactory, instanceNodeVal));
		//logging...
		loggingExecutorService.execute(new Runnable() {
			@Override
			public void run() {
				Instance instance=workflowMaster.getInstance(instanceNodeVal.getSequence());
				WorkTracking workTracking=
						workTracking(instanceNodeVal.getId(), path, 
								completeStatus, instance);
				trackingService.track(workTracking);
			}
		});
	}
	
	private WorkTracking workTracking(int workerId, String path,NodeStatus status,Instance instance){
		WorkTracking workTracking=new WorkTracking();
		workTracking.setWorkerId(String.valueOf(workerId));
		workTracking.setInstancePath(path);
		workTracking.setStatus(status);
		Date date=new Date();
		workTracking.setRecordTime(date.getTime());
		workTracking.setRecordTimeStr(JDateUtils.formatWithSeconds(date));
		InstanceNode instanceNode=instance.getInstanceNode(path);
		workTracking.setWorkerName(instanceNode.getNodeData().getName());
		workTracking.setId(JUniqueUtils.unique());
		workTracking.setOffset(-1);
		workTracking.setHashKey(String.valueOf(instance.getSequence()));
		return workTracking;
	}
	
	private int pathSequence(String path){
		String lastStr=path.substring(path.lastIndexOf("/"));
		return Integer.parseInt(lastStr.substring(lastStr.lastIndexOf("-")+1));
	}
	
	/**
	 * start a real worker .
	 * @param worker
	 * @param instancePath
	 * @param instance
	 */
	private void start(final int worker,final String instancePath,final Instance instance){
		
		if(!instance.getWorkflow().containsWorker(worker)){
			throw new RuntimeException("the worker["+worker+"] does not exist.");
		}
		
		if(!instance.getWorkflow().getWorkerPaths().containsKey(worker)){
			throw new RuntimeException("the worker["+worker+"] does not exist.");
		}
		
		logInfo("-find to start worker : "+worker +", instance : "+instance.getSequence()); 
		
		final WorkerPathVal workerPathVal=new WorkerPathVal();
		workerPathVal.setId(worker);
		workerPathVal.setTime(new Date().getTime());
		workerPathVal.setSequence(instance.getSequence());
		workerPathVal.setInstancePath(instancePath);
		workerPathVal.setConf(instance.getConf());
		
		boolean isStart=isStart(workerPathVal, instance);
		if(isStart){
			return;
		}
//		executor.setPath(instance.getWorkflow().getWorkerPaths().get(worker),
//				SerializerUtils.serialize(serializerFactory, workerPathVal));
		startRealWorker(workerPathVal, instance);
		loggingExecutorService.execute(new Runnable() {
			@Override
			public void run() {
				WorkTracking workTracking=
						workTracking(worker, instancePath, NodeStatus.READY, instance);
				trackingService.track(workTracking);
			}
		});
		
	}
	
	private void start(Instance instance){
		propagateWorkerPath(null,instance.getWorkflow().getNodeData(), null,instance);
	}
	
	/**
	 * watcher on the virtual instance node, detecting whether all children nodes/workers 
	 * are completed , and responsible for starting any node.
	 * @param instance
	 */
	private void attachInstanceChildPathWatcher(final Instance instance){
		for(String path:instance.getChildPathWatcherPaths()){
			final String _path=path;
			InstanceNode virtualNode=instance.getInstanceNodes().get(_path);
			final PathChildrenCache cache= executor.watchChildrenPath(_path, new ZooNodeChildrenCallback() {
				@Override
				public void call(List<ZooNode> nodes) {
					boolean done=true;
					boolean withError=false;
					boolean withSkip=false;
					int skipCount=nodes.size();
					for(ZooNode node:nodes){
						byte[] bytes=node.getDataAsPossible(executor);
						InstanceNodeVal instanceNodeVal=
								SerializerUtils.deserialize(serializerFactory, bytes, InstanceNodeVal.class);
						if(!instanceNodeVal.getStatus().isSkip()){
							skipCount--;
						}
						if(!isComplete(instanceNodeVal)){
							done=false;
						}else if(instanceNodeVal.getStatus().isCompleteWithError()){
							withError=true;
							instanceNodeVal.getStatus().getCause().forEach(new Consumer<Throwable>() {
								@Override
								public void accept(Throwable t) {
									instance.addError(node.getPath(),t);
								}
							});
						}
					}
					withSkip=skipCount==nodes.size();
					
					if(done){
						NodeStatus completeStatus=null;
						if(withError){
							completeStatus=NodeStatus.COMPLETE_ERROR;
						}else if(withSkip){
							completeStatus=NodeStatus.COMPLETE_SKIP;
						}else{
							completeStatus=NodeStatus.COMPLETE;
						}
						synchronized (sync) {
							InstaneCheck instaneCheck= instance.workerMaster().instaneCheck();
							instaneCheck.isStart(_path);
							if(!instaneCheck.isComplete(_path, completeStatus)){
								completeVirtual(instance, _path,completeStatus);
								if(path.equals(instance.getRootPath())){
									executeCompleteCommand(instance);
									executeRetryCommand(instance);
									if(withError){
										executeErrorCommand(instance);
									}
								}
							}
						}
						return;
					}
					
					Collections.sort(nodes, new Comparator<ZooNode>() {
						@Override
						public int compare(ZooNode o1, ZooNode o2) {
							return pathSequence(o1.getPath())-pathSequence(o2.getPath());
						}
					});
					
					if(!virtualNode.getNodeData().isParallel()){
						//start next node/worker in the virtual node
						InstanceNodeVal latestInsanceNode=null;
						for(int i=nodes.size()-1;i>-1;i--){
							ZooNode tempNode=nodes.get(i);
							byte[] bytes=tempNode.getDataAsPossible(executor);
							InstanceNodeVal instanceNodeVal=
									SerializerUtils.deserialize(serializerFactory, bytes, InstanceNodeVal.class);
							if(!isComplete(instanceNodeVal)){
								latestInsanceNode=instanceNodeVal;
							}
							else{
								break;
							}
						}
						if(latestInsanceNode!=null){
							NodeData nodeData=virtualNode.getNodeData();
							NodeData latestNodeData=null;
							for(NodeData temp:nodeData.getNodes()){
								if(temp.getId()==latestInsanceNode.getId()){
									latestNodeData=temp;
									break;
								}
							}
							if(withError){
								propagateCompleteDirectly(latestInsanceNode,  latestNodeData,virtualNode,
										instance);
							}
							else{
								propagateWorkerPath(latestInsanceNode,  latestNodeData,virtualNode,
										instance);
							}
						}
					}
				}
			}, zooKeeperExecutorService,PathChildrenCacheEvent.Type.CHILD_UPDATED);
			virtualNode.setPathChildrenCache(cache);
			instance.addInstanceNode(_path, virtualNode);
		}
		
	}
	
	private boolean isComplete(InstanceNodeVal instanceNodeVal) {
		return instanceNodeVal.getStatus().isComplete();
	}
	
	private int workerId(String path){
		return Integer.parseInt(path.substring(path.lastIndexOf("/")).split("-")[1]);
	}
	
	/**
	 * watcher on the path {@link #pluginWorkersPath(Workflow)} find all real workers in the workflow.
	 * @param workflow
	 */
	private synchronized void attachWorkersPathWatcher(final Workflow workflow){
		if(workflow.getPluginWorkersPathCache()!=null) return ;
		String _path=workflow.getPluginWorkersPath();
		if(!executor.exists(_path)){
			executor.createPath(_path);
		}
		final PathChildrenCache cache= executor.watchChildrenPath(_path, 
				new ZooNodeChildrenCallback() {
			@Override
			public void call(List<ZooNode> nodes) {
				for(ZooNode node:nodes){
					String path=node.getPath();
					int workerId=workerId(path);
					if(!workflow.getWorkerPaths().containsKey(workerId)){
						workflow.addWorkerPath(workerId, path);
					}
				}
			}
		}, zooKeeperExecutorService,PathChildrenCacheEvent.Type.CHILD_ADDED,
				PathChildrenCacheEvent.Type.CHILD_REMOVED);
		workflow.setPluginWorkersPathCache(cache);
	}
	
	private synchronized void createMasterMeta() throws Exception{
		logInfo("(Thread)+"+Thread.currentThread().getName()+" got worker-schedule leadership .... ");
		if(workflowMaster!=null) return;
		workflowMaster=new WorkflowMaster();
		attachWorfkowTriggerWatcher(null);
		attachWorfkowAddWatcher();
		registerLeaderInZookeeper();
		registerRPCInZookeeper();
		startServer();
		addCommonCommand();
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
	
	/**
	 * node leader code
	 * @return
	 */
	private LeaderNodeMeta registerLeaderInZookeeper(){
		workflowMaster.setLeaderNodeMeta(leaderNodeMeta);
        byte[] msg=
        		SerializerUtils.serialize(serializerFactory, leaderNodeMeta);
		if(!executor.exists(leaderRegisterPath())){
			executor.createPath(leaderRegisterPath(), msg);
		}
		else{
			executor.setPath(leaderRegisterPath(), msg);
		}
		return leaderNodeMeta;
	}
	
	private synchronized void addCommonCommand(){
		workflowMaster.addWorkflowCommand(new WorkflowCompleteCommand()) ;
		workflowMaster.addWorkflowCommand(new WorkflowRetryCommand());
		workflowMaster.addWorkflowCommand(new WorkflowErrorCommand());
	}
	
	private void executeErrorCommand(final Instance instance) {
		WorkflowErrorModel workflowErrorModel=new WorkflowErrorModel();
		workflowErrorModel.setIsntanceId(instance.getSequence());
		workflowErrorModel.setRecordTime(new Date().getTime());
		workflowErrorModel.setMessage(instance.getErrorMessage());
		workflowErrorModel.setWorkflow(instance.getWorkflow());
		workflowErrorModel.setWorkflowName(instance.getWorkflow().getName());
		workflowErrorModel.setConf(instance.getConf());
		executeCommand(workflowErrorModel);
	}
	
	private void executeRetryCommand(final Instance instance) {
		WorkflowRetryModel retryModel=new WorkflowRetryModel(instance.getWorkflow().getWorkflowMeta().getCount());
		retryModel.setCount(instance.getCount());
		retryModel.setIsntanceId(instance.getSequence());
		retryModel.setRecordTime(new Date().getTime());
		retryModel.setWorkflow(instance.getWorkflow());
		retryModel.setWorkflowName(instance.getWorkflow().getName());
		retryModel.setConf(instance.getConf());
		executeCommand(retryModel);
	}
	
	private void executeCompleteCommand(final Instance instance) {
		WorkflowCompleteModel completeModel=new WorkflowCompleteModel();
		completeModel.setIsntanceId(instance.getSequence());
		completeModel.setRecordTime(new Date().getTime());
		completeModel.setStatus(NodeStatus.COMPLETE);
		completeModel.setWorkflow(instance.getWorkflow());
		completeModel.setWorkflowName(instance.getWorkflow().getName());
		completeModel.setConf(instance.getConf());
		executeCommand(completeModel);
	}
	
	@SuppressWarnings("unchecked")
	private void executeCommand(WorkflowCommandModel commandModel){
		
		Collection<WorkflowCommand> commands=  workflowMaster.workflowCommands(commandModel.getClass());
		for(WorkflowCommand command:commands ){
			CommandResource commandResource =new CommandResource();
			commandResource.setExecutor(executor);
			command.execute(commandModel, commandResource);
		}
		
	}
	
	private Instance createInstance(Workflow workflow,Map<String, Object> conf){
		
		Long sequence=getSequence();
		Instance instance=new Instance();
		instance.setWorkflow(workflow);
		instance.setSequence(sequence);
		instance.setConf(conf);
		
		createInstancePath(instance.getWorkflow().getNodeData(),instance);
		
		attachInstanceChildPathWatcher(instance);
		
		attachWorfkowTriggerWatcher(workflow);
		
		workflow.setCount(workflow.getCount()+1);
		instance.setCount(workflow.getCount());
		
		workflowMaster.addInstance(sequence, instance);
		
		return instance;
	}
	
	public void startWorkflow(String name,Map<String, Object> conf){
		JAssert.isNotEmpty(name);
		Workflow workflow=workflowMaster.getWorkflow(name);
		if(workflow==null){
			throw new RuntimeException("workflow is missing, to add workflow in the container.");
		}
		Instance instance=createInstance(workflow,conf);
		start(instance);
	}
	
	
	/**
	 * a registering queue , workflow need be registered 
	 * in the node {@link #workflowTrigger(Workflow)}  in the zookeeper.
	 * @param workflow
	 * @see IWorkflowService#triggerWorkflow(String, Map)
	 */
	@Deprecated
	private void attachWorfkowTriggerWatcher(final Workflow workflow){
		
		if(workflow!=null){
			if(workflow.getWorkflowTriggerCache()!=null) return ;
		}
		
		
		final String path=workflowTrigger(workflow);
		if(!executor.exists(path)){
			executor.createPath(path);
		}
		NodeCache cache=executor.watchPath(path, new ZooNodeCallback() {
			
			@Override
			public void call(ZooNode node) {
				WorkflowMeta workflowMeta=
						SerializerUtils.deserialize(serializerFactory, node.getDataAsPossible(executor), WorkflowMeta.class);
				startWorkflow(workflowMeta.getName(), Maps.newHashMap());
			}
		}, Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, workflowPath(workflow)+"{watcher workflow trigger}");
			}
		}));
		if(workflow==null){
			workflowMaster.setWorkflowTriggerCache(cache);
		}else{
			workflow.setWorkflowTriggerCache(cache);
		}
	}
	
	/**
	 * all workflows should be registered in the {@link #workflowAddPath()} as a child node
	 * in the zookeeper
	 */
	private synchronized void attachWorfkowAddWatcher(){
		final String workflowAddPath=workflowAddPath();
		if(!executor.exists(workflowAddPath)){
			executor.createPath(workflowAddPath);
		}
		PathChildrenCache cache=  executor.watchChildrenPath(workflowAddPath, new ZooNodeChildrenCallback() {
			
			@Override
			public void call(List<ZooNode> nodes) {
				for(ZooNode node:nodes){
					byte[] bytes=node.getDataAsPossible(executor);
					WorkflowMeta workflowMeta=
							SerializerUtils.deserialize(serializerFactory, bytes, WorkflowMeta.class);
					addWorkflow(workflowMeta);
				}
			}
		} , Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, workflowAddPath+"{watcher workflow add}");
			}
		}), PathChildrenCacheEvent.Type.CHILD_ADDED
				,PathChildrenCacheEvent.Type.CHILD_REMOVED
				,PathChildrenCacheEvent.Type.CHILD_UPDATED);
		workflowMaster.setWorkfowAddCache(cache);
	}
	
	
	/**
	 * start a real / virtual path , whatever a real worker should be started.
	 * if the node is virtual, find into its children.
	 * @param triggerInstanceNodeVal point to the same node as triggerNodeData
	 * @param triggerNodeData
	 * @param virtualNode
	 * @param instance
	 */
	private void propagateWorkerPath(InstanceNodeVal triggerInstanceNodeVal,
			NodeData triggerNodeData,InstanceNode virtualNode,Instance instance){
		if(triggerNodeData.hasChildren()){ // it's virtual node  
			if(triggerNodeData.isParallel()){
				//the node is parallel , so we need start all children nodes at the time
				for(NodeData thisNodeData:triggerNodeData.getNodes()){
					propagateWorkerPath(triggerInstanceNodeVal,
							thisNodeData, virtualNode,instance);
				}
			}
			else{
				//we get the first node to start.
				NodeData thisNodeData=triggerNodeData.getNodes().get(0);
				propagateWorkerPath(triggerInstanceNodeVal,
						thisNodeData, virtualNode,instance);
			}
		}else{ // it's real node, directly start 
			start(triggerNodeData.getId(), instancePath(triggerNodeData.getPath(), instance), instance);
		}
	}
	
	/**
	 * complete all sub-node/worker directly, no need start any worker
	 * @param triggerInstanceNodeVal point to the same node as triggerNodeData
	 * @param triggerNodeData
	 * @param virtualNode
	 * @param instance
	 */
	private void propagateCompleteDirectly(InstanceNodeVal triggerInstanceNodeVal,
			NodeData triggerNodeData,InstanceNode virtualNode,Instance instance){
		if(triggerNodeData.hasChildren()){ // it's virtual node  
			if(triggerNodeData.isParallel()){
				//the node is parallel , so we need start all children nodes at the time
				for(NodeData thisNodeData:triggerNodeData.getNodes()){
					propagateCompleteDirectly(triggerInstanceNodeVal, 
							thisNodeData,virtualNode, instance);
				}
			}
			else{
				//we get the first node to start.
				NodeData thisNodeData=triggerNodeData.getNodes().get(0);
				propagateCompleteDirectly(triggerInstanceNodeVal, 
						thisNodeData,virtualNode, instance);
			}
		}else{ // it's real node, directly complete 
			c(instancePath(triggerNodeData.getPath(), instance), NodeStatus.COMPLETE_SKIP);
		}
	}
	
	
	
	/**
	 * attempt to latch leader.
	 */
	private void startLeader(){
		
		try {
			leaderLatch.start();
			leaderLatch.await();
			createMasterMeta();
			
			while(true){
				try{
					synchronized (this) {
						wait();
					}
				}catch (InterruptedException e) {
				}
			}
			
		} catch (Exception e) {
			logError(e);
			throw new IllegalStateException(e);
		}finally {
			try {
				if(leaderLatch.hasLeadership()){
					leaderLatch.close();
				}
			} catch (IOException e) {
				logError(e);
			}
		}
	}
	
	
	/**
	 * start the real worker...
	 * @param workerPathVal
	 * @param instance
	 */
	private boolean startRealWorker(final WorkerPathVal workerPathVal,Instance instance){
		final int workerId=workerPathVal.getId();
		WorkerMaster workerMaster=instance.workerMaster();
		
		String workerExecutingBasePath=pluginWorkerInstancePath(workerId,instance);
		final String workerExecutingPath=workerExecutingBasePath+"/exenodes";
		final String workerExecutingErrorPath=workerExecutingBasePath+"/error";
		executor.createPath(workerExecutingPath, 
				SerializerUtils.serialize(serializerFactory, workerPathVal));
		executor.createPath(workerExecutingErrorPath, 
				SerializerUtils.serialize(serializerFactory, workerPathVal));
		
		WorkerExecutingPathVal workerExecutingPathVal=new WorkerExecutingPathVal();
		workerExecutingPathVal.setWorkerExecutingPath(workerExecutingPath);
		workerExecutingPathVal.setWorkerPathVal(workerPathVal);
		
		workerExecutingPathVal.setWorkerExecutingErrorPath(workerExecutingErrorPath); 
		// add watcher on the worker executing path for every instance
		final PathChildrenCache cache= 
		executor.watchChildrenPath(workerExecutingPath, new ZooNodeChildrenCallback() {
			@Override
			public void call(List<ZooNode> nodes) {
				if(nodes.isEmpty()){
					try{
						WorkerPathVal workerPathVal= 
								SerializerUtils.deserialize(serializerFactory, 
										executor.getPath(workerExecutingErrorPath), WorkerPathVal.class);
						List<String> errorPaths=executor.getChildren(workerExecutingErrorPath);
						StringBuffer buffer=new StringBuffer();
						for(String errorPath:errorPaths){
							String error=SerializerUtils.deserialize(serializerFactory, 
									executor.getPath(workerExecutingErrorPath+"/"+errorPath)
									, String.class);
							buffer.append(error+"\r\n------------------------------------+\r\n");
						}
						completeWorker(workerPathVal.getInstancePath(),instance,
								buffer.length()>0?
								new RuntimeException(buffer.toString()):null);
					}finally{
						try {
							workerMaster.closeInstance(workerExecutingPath);
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
					}
				}
			}
		}, zooKeeperExecutorService,PathChildrenCacheEvent.Type.CHILD_REMOVED);
		workerMaster.addProcessorsWather(workerExecutingPath, cache);
		
		// notify sub-processors of worker
		String processorPath=pluginWorkerProcessorPath(workerId, instance);
		List<String> chls=executor.getChildren(processorPath);
		for(String chPath:chls){
			String relProcessorPath=processorPath+"/"+chPath;
			if(!executor.getChildren(relProcessorPath).isEmpty()){
				WorkerNodeMeta workerNodeMeta=
					SerializerUtils.deserialize(serializerFactory, 
							executor.getPath(relProcessorPath), WorkerNodeMeta.class);
				IWorkerService workerService=
						SimpleInterfaceImplUtil.syncProxy(IWorkerService.class,
								executorPool.get(workerNodeMeta.getHost(),
										workerNodeMeta.getPort()));
				workerService.notifyWorkers(workerExecutingPathVal);
			}else{
				//may delete the node (broken from the zookeeper)
				executor.deletePath(relProcessorPath);
			}
		}
		return true;
	}

	private synchronized boolean isStart(final WorkerPathVal workerPathVal, Instance instance) {
		if(instance.workerMaster().instaneCheck().isStart(workerPathVal.getInstancePath())){
			logInfo(" for instance["+workerPathVal.getSequence()+"] is triggered, skip this calling");
			return true;
		}
		return false;
	}
	
	/**
	 * complete the real worker...
	 * @param path
	 * @param instance
	 * @param t
	 */
	private void completeWorker(final String path,Instance instance,Throwable t){
		final InstanceNodeVal instanceNodeVal=
				SerializerUtils.deserialize(serializerFactory, executor.getPath(path),
						InstanceNodeVal.class);
		NodeStatus nodeStatus=null;
		if(t==null){
			nodeStatus=NodeStatus.COMPLETE;
		}else{
			nodeStatus=NodeStatus.COMPLETE_ERROR;
			nodeStatus.setThrowable(t);
		}
		instanceNodeVal.setStatus(nodeStatus);
		
		executor.setPath(path, 
				SerializerUtils.serialize(serializerFactory, instanceNodeVal));
		loggingExecutorService.execute(new Runnable() {
			@Override
			public void run() {
				WorkTracking workTracking=
						workTracking(instanceNodeVal.getId(), path,
								instanceNodeVal.getStatus(),instance);
				trackingService.track(workTracking);
			}
		});
	}
	
	ZookeeperExecutor getExecutor() {
		return executor;
	}
	
	public Map getConf() {
		return conf;
	}

	private void logError(Exception e) {
		LOGGER.error(getMessage(e.getMessage()), e);
	}
	
	private void logInfo(String msg) {
		LOGGER.info(getMessage(msg));
	}
	
	private String getMessage(String msg){
		return String.format("---leader-name[%s]--%s---", new Object[]{name,msg});
	}
	
	public int getId() {
		return id;
	}
}
