package j.jave.kernal.streaming.coordinator;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.coordinator.WorkerMaster.InstaneCheck;
import j.jave.kernal.streaming.coordinator.Workflow.WorkflowCheck;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand.WorkflowCommandModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowCompleteModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowErrorModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowRetryModel;
import j.jave.kernal.streaming.coordinator.rpc.worker.IWorkerService;
import j.jave.kernal.streaming.coordinator.services.tracking.TrackingService;
import j.jave.kernal.streaming.netty.client.SimpleInterfaceImplUtil;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooNode;
import j.jave.kernal.streaming.zookeeper.ZooNodeChildrenCallback;

public class InstanceCtl {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(InstanceCtl.class);

	private final NodeLeader nodeLeader;
	
	private final WorkflowMaster workflowMaster;
	
	private DistAtomicLong atomicLong;
	
	private final Object sync=new Object();
	
	@JsonIgnore
	private final transient ZookeeperExecutor executor;
	
	@JsonIgnore
	private final transient JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();

	public InstanceCtl(NodeLeader nodeLeader, WorkflowMaster workflowMaster) {
		this.nodeLeader = nodeLeader;
		this.workflowMaster = workflowMaster;
		this.executor=nodeLeader.getExecutor();
		this.atomicLong=new DistAtomicLong(executor);
	}
	
	public static abstract class CacheType{
		private static final String CHILD="patch_child";
	}
	
	/**
	 * start the task.
	 * @param task
	 */
	void startTask(Task task) {
		if(task!=null){
			Workflow workflow=workflowMaster.getWorkflow(task.getWorkflowName());
			if(workflow==null){
				logInfo(" the workflow["+task.getWorkflowName()+"] is removed; task id : "+task.getId());
				return;
			}
			WorkflowCheck workflowCheck=workflow.workflowCheck();
			if(workflowCheck.isOffline()){
				logInfo(" the workflow["+task.getWorkflowName()+"] is offline;task id : "+task.getId());
				return;
			}
			
			Instance instance=createInstance(workflow,
					task.getParams());
			try{
				if(!workflowCheck.tryLock(instance.getSequence())){
					throw new IllegalStateException("workflow is locked : "+workflowCheck.getLockSequence());
				}
				start(instance);
			}catch (Exception e) {
				// the virtual node is completed, set flag to complete
				NodeStatus nodeStatus=NodeStatus.COMPLETE_ERROR.setThrowable(e);
				c(instance,instance.getRootPath(),nodeStatus);
				try {
					instance.addError(instance.getRootPath(), e);
					completeInstance(instance,nodeStatus);
				} catch (Exception e1) {
					logError(e1);
				}
			}
			
		}
	}
	
	
	private void start(Instance instance){
		propagateWorkerPath(null,instance.getWorkflow().getNodeData(), null,instance);
	}
	
	/**
	 * start a real worker .
	 * @param worker
	 * @param instancePath
	 * @param instance
	 */
	private void start(final int worker,final String instancePath,final Instance instance){
		
		if(!instance.getWorkflow().containsWorker(worker)){
			throw new RuntimeException("the worker["+worker+"] is invalid.");
		}
		
		if(!instance.getWorkflow().getWorkerPaths().containsKey(worker)){
			throw new RuntimeException("the worker["+worker+"] does not exist.");
		}
		
		logInfo("- ready to start worker : "+worker +", instance : "+instance.getSequence()); 
		
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
		logInfo("- now to start worker : "+worker +", instance : "+instance.getSequence()); 
		
//		executor.setPath(instance.getWorkflow().getWorkerPaths().get(worker),
//				SerializerUtils.serialize(serializerFactory, workerPathVal));
		startRealWorker(workerPathVal, instance);
		loggingExecutorService().execute(new Runnable() {
			@Override
			public void run() {
				WorkTracking workTracking=
						workTracking(worker, instancePath, NodeStatus.READY, instance);
				trackingService().track(workTracking);
			}
		});
		
	}
	
	
	
	
	/**
	 * watcher on the virtual instance node, detecting whether all children nodes/workers 
	 * are completed , and responsible for starting any node.
	 * @param instance
	 */
	private void attachInstanceChildPathWatcher(final Instance instance){
		for(String path:instance.getChildPathWatcherPaths()){
			final String _path=path;
			InstanceNode virtualNode=instance.getInstanceNode(_path);
			final PathChildrenCache cache= executor.watchChildrenPath(_path, new ZooNodeChildrenCallback() {
				@Override
				public void call(List<ZooNode> nodes) {
					try{
						
						//check if the virtual is already completed
						byte[] vbytes=executor.getPath(_path);
						if(vbytes!=null&&vbytes.length>0){
							InstanceNodeVal virtualNodeVal=
									SerializerUtils.deserialize(serializerFactory, vbytes, InstanceNodeVal.class);
							if(virtualNodeVal.getStatus().isComplete()){
								return ;
							}
						}
						
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
								if(!instaneCheck.isComplete(_path, completeStatus)){
									completeVirtual(instance, _path,completeStatus);
									if(path.equals(instance.getRootPath())){
										executeCompleteCommand(instance);
//										executeRetryCommand(instance);  // avoid turn on this function , should close retry function
										if(withError){
											executeErrorCommand(instance);
										}
										completeInstance(instance,completeStatus);
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
					}catch (Exception e) {
						// the virtual node is completed, set flag to complete
						NodeStatus nodeStatus=NodeStatus.COMPLETE_ERROR.setThrowable(e);
						c(instance,instance.getRootPath(),nodeStatus);
						try {
							instance.addError(_path, e);
							completeInstance(instance,nodeStatus);
						} catch (Exception e1) {
							LOGGER.error(e1.getMessage(), e1);
						}
					}
					
				}
			}, zooKeeperExecutorService(),PathChildrenCacheEvent.Type.CHILD_UPDATED);
			virtualNode.setPathChildrenCache(cache);
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
		
		workflow.setCount(workflow.getCount()+1);
//		instance.setCount(workflow.getCount());
		
		workflowMaster.addInstance(sequence, instance);
		
		return instance;
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
			start(triggerNodeData.getId(), nodeLeader.instancePath(triggerNodeData.getPath(), instance), instance);
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
			c(instance,nodeLeader.instancePath(triggerNodeData.getPath(), instance), NodeStatus.COMPLETE_SKIP);
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
		
		String workerExecutingBasePath=nodeLeader.pluginWorkerInstancePath(workerId,instance);
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
		}, zooKeeperExecutorService(),PathChildrenCacheEvent.Type.CHILD_REMOVED);
		workerMaster.addProcessorsWather(workerExecutingPath, cache);
		
		// notify sub-processors of worker
		String processorPath=nodeLeader.pluginWorkerProcessorPath(workerId, instance.getWorkflow());
		List<String> chls=executor.getChildren(processorPath);
		boolean hasProcessor=false;
		for(String chPath:chls){
			String relProcessorPath=processorPath+"/"+chPath;
			if(!executor.getChildren(relProcessorPath).isEmpty()){
				hasProcessor=true;
				WorkerNodeMeta workerNodeMeta=
					SerializerUtils.deserialize(serializerFactory, 
							executor.getPath(relProcessorPath), WorkerNodeMeta.class);
				IWorkerService workerService=
						SimpleInterfaceImplUtil.syncProxy(IWorkerService.class,
								nodeLeader.getChannelExecutorPool().get(workerNodeMeta.getHost(),
										workerNodeMeta.getPort()));
				workerService.notifyWorkers(workerExecutingPathVal);
			}else{
				//may delete the node (broken from the zookeeper)
				executor.deletePath(relProcessorPath);
			}
		}
		if(!hasProcessor){
			throw new IllegalStateException(" worker["+workerPathVal.getId()+"] is offline");
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
		instance.updateInstanceNodeVal(path, instanceNodeVal);
		instance.workerMaster().instaneCheck().isComplete(path, nodeStatus);
		executor.setPath(path, 
				SerializerUtils.serialize(serializerFactory, instanceNodeVal));
		loggingExecutorService().execute(new Runnable() {
			@Override
			public void run() {
				WorkTracking workTracking=
						workTracking(instanceNodeVal.getId(), path,
								instanceNodeVal.getStatus(),instance);
				trackingService().track(workTracking);
			}
		});
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
	
	private void completeInstance(final Instance instance,NodeStatus nodeStatus) throws Exception {
		instance.close();
		workflowMaster.completeInstance(instance, getTaskCallBack());
	}
	
	
	private boolean isComplete(InstanceNodeVal instanceNodeVal) {
		return instanceNodeVal.getStatus().isComplete();
	}
	
	private long getSequence(){
		return atomicLong.getSequence();
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
			String instancePath=nodeLeader.instancePath(c.getPath(),instance);
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
		c(instance,_path,completeStatus);
		//close instance children node watcher, release IO
		close(instance.getSequence(), _path, CacheType.CHILD);
	}
	
	/**
	 * is;s call  when the logical/virtual node is completed.
	 * @param path
	 */
	private void c(Instance instance,final String path,final NodeStatus completeStatus){
		final InstanceNodeVal instanceNodeVal=
				SerializerUtils.deserialize(serializerFactory, executor.getPath(path), InstanceNodeVal.class);
		instanceNodeVal.setStatus(completeStatus);
		instanceNodeVal.setTime(new Date().getTime());
		
		instance.updateInstanceNodeVal(path, instanceNodeVal);
		instance.workerMaster().instaneCheck().isComplete(path, completeStatus);
		
		executor.setPath(path, SerializerUtils.serialize(serializerFactory, instanceNodeVal));
		//logging...
		loggingExecutorService().execute(new Runnable() {
			@Override
			public void run() {
				Instance instance=workflowMaster.getInstance(instanceNodeVal.getSequence());
				WorkTracking workTracking=
						workTracking(instanceNodeVal.getId(), path, 
								completeStatus, instance);
				trackingService().track(workTracking);
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
	
	
	TaskCallBack getTaskCallBack() {
		return new TaskCallBack() {
			@Override
			public void call(Task task) {
				startTask(task);
			}
		};
	}
	
	
	
	
	
	
	
	
	
	
	void logError(Exception e) {
		nodeLeader.logError(e);
	}
	
	void logInfo(String msg) {
		nodeLeader.logInfo(msg);
	}
	
	
	private ExecutorService loggingExecutorService(){
		return nodeLeader.getLoggingExecutorService();
	}
	
	private TrackingService trackingService() {
		return nodeLeader.getTrackingService();
	}
	
	ExecutorService zooKeeperExecutorService(){
		return nodeLeader.zooKeeperExecutorService();
	}
	
	
	
	
	
	
	
}
