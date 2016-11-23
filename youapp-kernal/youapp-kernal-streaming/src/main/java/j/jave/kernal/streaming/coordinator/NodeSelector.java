package j.jave.kernal.streaming.coordinator;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.coordinator.NodeData.NodeStatus;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand.WorkflowCommandModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowCompleteCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowCompleteModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowRetryCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowRetryModel;
import j.jave.kernal.streaming.kafka.KafkaProducerConfig;
import j.jave.kernal.streaming.kafka.ProducerConnector;
import j.jave.kernal.streaming.kafka.ProducerConnector.ProducerExecutor;
import j.jave.kernal.streaming.kafka.SimpleProducer;
import j.jave.kernal.streaming.zookeeper.ZooNode;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooNodeCallback;
import j.jave.kernal.streaming.zookeeper.ZooNodeChildrenCallback;

@SuppressWarnings({"serial","rawtypes"})
public class NodeSelector implements Serializable{
	
	private Map conf;
	
	private String name;
	
	private String logPrefix;
	
	private String basePath=CoordinatorPaths.BASE_PATH;
	
	private ZookeeperExecutor executor;
	
	private LeaderLatch leaderLatch;
	
	private DistAtomicLong atomicLong;

	private WorkflowMaster workflowMaster;
	
	private SimpleProducer simpleProducer;
	
	private ExecutorService executorService=Executors.newFixedThreadPool(3);
	
	public static abstract class CacheType{
		private static final String CHILD="patch_child";
	}
	
	private static NodeSelector NODE_SELECTOR;
	
	public synchronized static NodeSelector startup(String name,ZookeeperExecutor executor,Map conf){
		NodeSelector nodeSelector=NODE_SELECTOR;
		if(nodeSelector==null){
			nodeSelector=new NodeSelector(executor);
			nodeSelector.conf=conf;
			nodeSelector.name=name;
			nodeSelector.logPrefix="selector["+nodeSelector.name+"] ";
			KafkaProducerConfig producerConfig=KafkaProducerConfig.build(conf);
			ProducerConnector producerConnecter=new ProducerConnector(producerConfig);
			ProducerExecutor<String,String> producerExecutor=  producerConnecter.connect();
			SimpleProducer simpleProducer =new SimpleProducer(producerExecutor, 
					"workflow-instance-track");
			nodeSelector.simpleProducer=simpleProducer;
			NODE_SELECTOR=nodeSelector;
		}
		return NODE_SELECTOR;
	}
	
	private NodeSelector(ZookeeperExecutor executor) {
		this.executor = executor;
		this.atomicLong=new DistAtomicLong(executor);
		leaderLatch=new LeaderLatch(executor.backend(),
				leaderPath());
		leaderLatch.addListener(new LeaderLatchListener() {
			
			@Override
			public void notLeader() {
				if(workflowMaster==null) return;
				System.out.println(logPrefix()+" lose leadership .... ");
				try {
					workflowMaster.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				workflowMaster=null;
			}
			
			@Override
			public void isLeader() {
				System.out.println(logPrefix()+" is leadership .... ");
				createMasterMeta();
			}
		}, Executors.newFixedThreadPool(1));
		
		Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "worker-selector");
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
	
	String instancePath(String path,Instance instance){
		return basePath+"/instance/"+instance.getWorkflow().getName()+"/"+instance.getSequence()+path;
	}
	
	String workflowTrigger(final Workflow workflow){
		String triggerPath=basePath+"/workflow-trigger";
		return workflow==null?triggerPath:(triggerPath+"/"+workflow.getName());
	}
	
	String workflowAddPath(){
		return basePath+"/workers-collection-repo";
	}
	
	String leaderPath(){
		return basePath+"/leader-latch";
	}
	
	String basePath(){
		return basePath;
	}
	
	
	String simpleTrackingPath(){
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
				e.printStackTrace();
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
			executor.createPath(instancePath
					,JJSON.get().formatObject(instanceNodeVal).getBytes(Charset.forName("utf-8")));
			
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
			,final String completeStatus) {
		// the virtual node is completed, set flag to complete
		c(_path,completeStatus);
		//close instance children node watcher, release IO
		close(instance.getSequence(), _path, CacheType.CHILD);
	}
	
	/**
	 * is;s call  when the logical/virtual node is completed.
	 * @param path
	 */
	private void c(final String path,final String completeStatus){
		final InstanceNodeVal instanceNodeVal=JJSON.get().parse(new String(executor.getPath(path),Charset.forName("utf-8")), InstanceNodeVal.class);
		instanceNodeVal.setStatus(completeStatus);
		instanceNodeVal.setTime(new Date().getTime());
		executor.setPath(path, JJSON.get().formatObject(instanceNodeVal));
		//logging...
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				Instance instance=workflowMaster.getInstance(instanceNodeVal.getSequence());
				WorkTracking workTracking=
						workTracking(instanceNodeVal.getId(), path, 
								completeStatus, instance);
				simpleProducer.send(workTracking);
			}
		});
	}
	
	private WorkTracking workTracking(int workerId, String path,String status,Instance instance){
		WorkTracking workTracking=new WorkTracking();
		workTracking.setWorkerId(String.valueOf(workerId));
		workTracking.setInstancePath(path);
		workTracking.setStatus(status);
		workTracking.setRecordTime(new Date().getTime());
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
		final WorkerPathVal workerPathVal=new WorkerPathVal();
		workerPathVal.setId(worker);
		workerPathVal.setTime(new Date().getTime());
		workerPathVal.setSequence(instance.getSequence());
		workerPathVal.setInstancePath(instancePath);
		executor.setPath(instance.getWorkflow().getWorkerPaths().get(worker),
				JJSON.get().formatObject(workerPathVal));
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				WorkTracking workTracking=
						workTracking(worker, instancePath, NodeStatus.READY, instance);
				simpleProducer.send(workTracking);
			}
		});
		
	}
	
	private void start(Instance instance){
		propagateWorkerPath(null, null, instance.getWorkflow().getNodeData(), instance);
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
					for(ZooNode node:nodes){
						byte[] bytes=node.getData();
						if(bytes==null){
							bytes=executor.getPath(node.getPath());
						}
						InstanceNodeVal instanceNodeVal=JJSON.get().parse(new String(bytes, Charset.forName("utf-8")),
								InstanceNodeVal.class);
						if(!isComplete(instanceNodeVal)){
							done=false;
						}
						else{
							if(NodeStatus.COMPLETE_ERROR.equals(instanceNodeVal.getStatus())){
								withError=true;
							}
						}
					}
					
					if(done){
						String completeStatus=withError?NodeStatus.COMPLETE_ERROR
								:NodeStatus.COMPLETE;
						completeVirtual(instance, _path,completeStatus);
						if(path.equals(instance.getRootPath())){
							executeCompleteCommand(instance);
							executeRetryCommand(instance);
						}
						return;
					}
					
					Collections.sort(nodes, new Comparator<ZooNode>() {
						@Override
						public int compare(ZooNode o1, ZooNode o2) {
							return pathSequence(o1.getPath())-pathSequence(o2.getPath());
						}
					});
					
					if("0".equals(virtualNode.getNodeData().getParallel())){
						//start next node/worker in the virtual node
						InstanceNodeVal latestNode=null;
						for(int i=nodes.size()-1;i>-1;i--){
							ZooNode tempNode=nodes.get(i);
							byte[] bytes=tempNode.getData();
							if(bytes==null){
								bytes=executor.getPath(tempNode.getPath());
							}
							InstanceNodeVal instanceNodeVal=JJSON.get().parse(
									new String(bytes, Charset.forName("utf-8")),
									InstanceNodeVal.class);
							if(!isComplete(instanceNodeVal)){
								latestNode=instanceNodeVal;
							}
							else{
								break;
							}
						}
						if(latestNode!=null){
							NodeData nodeData=virtualNode.getNodeData();
							NodeData find=null;
							for(NodeData temp:nodeData.getNodes()){
								if(temp.getId()==latestNode.getId()){
									find=temp;
									break;
								}
							}
							if(withError){
								propagateCompleteDirectly(latestNode, virtualNode, find,
										instance);
							}
							else{
								propagateWorkerPath(latestNode, virtualNode, find,
										instance);
							}
						}
					}
				}
			}, Executors.newFixedThreadPool(1, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, workflowPath(instance.getWorkflow())+"{watch children}");
				}
			}),PathChildrenCacheEvent.Type.CHILD_UPDATED);
			virtualNode.setPathChildrenCache(cache);
			instance.addInstanceNode(_path, virtualNode);
		}
		
	}
	
	private boolean isComplete(InstanceNodeVal instanceNodeVal) {
		return NodeStatus.COMPLETE.equals(instanceNodeVal.getStatus())
				||NodeStatus.COMPLETE_ERROR.equals(instanceNodeVal.getStatus());
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
		}, Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, workflowPath(workflow)+"{watch works children}");
			}
		}),PathChildrenCacheEvent.Type.CHILD_ADDED,
				PathChildrenCacheEvent.Type.CHILD_REMOVED);
		workflow.setPluginWorkersPathCache(cache);
	}
	
	private synchronized void createMasterMeta(){
		System.out.println(logPrefix()+" got workflow leader ... "); 
		if(workflowMaster!=null) return;
		workflowMaster=new WorkflowMaster();
		attachWorfkowTriggerWatcher(null);
		attachWorfkowAddWatcher();
		addCommonCommand();
	}
	
	private synchronized void addCommonCommand(){
		workflowMaster.addWorkflowCommand(new WorkflowCompleteCommand()) ;
		workflowMaster.addWorkflowCommand(new WorkflowRetryCommand());
	}
	
	private void executeRetryCommand(final Instance instance) {
		WorkflowRetryModel retryModel=new WorkflowRetryModel(instance.getWorkflow().getWorkflowMeta().getCount());
		retryModel.setCount(instance.getCount());
		retryModel.setIsntanceId(instance.getSequence());
		retryModel.setRecordTime(new Date().getTime());
		retryModel.setWorkflow(instance.getWorkflow());
		retryModel.setWorkflowName(instance.getWorkflow().getName());
		executeCommand(retryModel);
	}
	
	private void executeCompleteCommand(final Instance instance) {
		WorkflowCompleteModel completeModel=new WorkflowCompleteModel();
		completeModel.setIsntanceId(instance.getSequence());
		completeModel.setRecordTime(new Date().getTime());
		completeModel.setStatus(NodeStatus.COMPLETE);
		completeModel.setWorkflow(instance.getWorkflow());
		completeModel.setWorkflowName(instance.getWorkflow().getName());
		executeCommand(completeModel);
	}
	
	private void executeCommand(WorkflowCommandModel commandModel){
		
		Collection<WorkflowCommand> commands=  workflowMaster.workflowCommands(commandModel.getClass());
		for(WorkflowCommand command:commands ){
			CommandResource commandResource =new CommandResource();
			commandResource.setExecutor(executor);
			command.execute(commandModel, commandResource);
		}
		
	}
	
	private Instance createInstance(Workflow workflow){
		
		Long sequence=getSequence();
		Instance instance=new Instance();
		instance.setWorkflow(workflow);
		instance.setSequence(sequence);
		
		createInstancePath(instance.getWorkflow().getNodeData(),instance);
		
		attachInstanceChildPathWatcher(instance);
		
		attachWorfkowTriggerWatcher(workflow);
		
		workflow.setCount(workflow.getCount()+1);
		instance.setCount(workflow.getCount());
		
		workflowMaster.addInstance(sequence, instance);
		
		return instance;
	}
	
	/**
	 * a registering queue , any workflow need start
	 *  should be registered override the node {@link #workflowTrigger(Workflow)}  in the zookeeper.
	 * @param workflow
	 */
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
				WorkflowMeta workflowMeta=JJSON.get().parse(node.getStringData(), WorkflowMeta.class);
				Workflow workflow=workflowMaster.getWorkflow(workflowMeta.getName());
				if(workflow==null){
					throw new RuntimeException("workflow is missing, to add workflow in the container.");
				}
				Instance instance=createInstance(workflow);
				start(instance);
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
					byte[] bytes=node.getData();
					if(bytes==null){
						bytes=executor.getPath(node.getPath());
					}
					WorkflowMeta workflowMeta=JJSON.get().parse(JStringUtils.utf8(bytes), WorkflowMeta.class);
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
	 * @param triggerInstanceNodeVal
	 * @param triggerInstanceNode
	 * @param nodeData
	 * @param instance
	 */
	private void propagateWorkerPath(InstanceNodeVal triggerInstanceNodeVal,
			InstanceNode virtualNode,NodeData triggerNodeData,Instance instance){
		if(triggerNodeData.hasChildren()){ // it's virtual node  
			if("1".equals(triggerNodeData.getParallel())){
				//the node is parallel , so we need start all children nodes at the time
				for(NodeData thisNodeData:triggerNodeData.getNodes()){
					propagateWorkerPath(triggerInstanceNodeVal, virtualNode,
							thisNodeData, instance);
				}
			}
			else{
				//we get the first node to start.
				NodeData thisNodeData=triggerNodeData.getNodes().get(0);
				propagateWorkerPath(triggerInstanceNodeVal, virtualNode,
						thisNodeData, instance);
			}
		}else{ // it's real node, directly start 
			start(triggerNodeData.getId(), instancePath(triggerNodeData.getPath(), instance), instance);
		}
	}
	
	/**
	 * complete all sub-node/worker directly, no need start any worker
	 * @param triggerInstanceNodeVal
	 * @param virtualNode
	 * @param triggerNodeData
	 * @param instance
	 */
	private void propagateCompleteDirectly(InstanceNodeVal triggerInstanceNodeVal,
			InstanceNode virtualNode,NodeData triggerNodeData,Instance instance){
		if(triggerNodeData.hasChildren()){ // it's virtual node  
			if("1".equals(triggerNodeData.getParallel())){
				//the node is parallel , so we need start all children nodes at the time
				for(NodeData thisNodeData:triggerNodeData.getNodes()){
					propagateCompleteDirectly(triggerInstanceNodeVal, virtualNode,
							thisNodeData, instance);
				}
			}
			else{
				//we get the first node to start.
				NodeData thisNodeData=triggerNodeData.getNodes().get(0);
				propagateCompleteDirectly(triggerInstanceNodeVal, virtualNode,
						thisNodeData, instance);
			}
		}else{ // it's real node, directly complete 
			c(instancePath(triggerNodeData.getPath(), instance), NodeStatus.COMPLETE_ERROR);
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
			e.printStackTrace();
		}finally {
			try {
				if(leaderLatch.hasLeadership()){
					leaderLatch.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	ZookeeperExecutor getExecutor() {
		return executor;
	}
	
	public Map getConf() {
		return conf;
	}
	
	private String logPrefix(){
		return logPrefix;
	}
}
