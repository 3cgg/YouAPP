package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.coordinator.Workflow.WorkflowCheck;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand.WorkflowCommandModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowCompleteCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowErrorCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowRetryCommand;
import j.jave.kernal.streaming.coordinator.rpc.leader.ExecutingWorker;
import j.jave.kernal.streaming.coordinator.rpc.leader.IWorkflowService;
import j.jave.kernal.streaming.coordinator.services.taskrepo.TaskRepo;
import j.jave.kernal.streaming.coordinator.services.taskrepo.ZKTaskRepo;
import j.jave.kernal.streaming.coordinator.services.workflowmetarepo.ChangedCallBack;
import j.jave.kernal.streaming.coordinator.services.workflowmetarepo.WorkflowMetaRepo;
import j.jave.kernal.streaming.coordinator.services.workflowmetarepo.ZKWorkflowMetaRepo;
import j.jave.kernal.streaming.netty.server.SimpleHttpNioChannelServer;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooNode;
import j.jave.kernal.streaming.zookeeper.ZooNodeCallback;
import j.jave.kernal.streaming.zookeeper.ZooNodeChildrenCallback;

public class WorkflowMaster implements JModel ,Closeable{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(NodeWorker.class);
	
	@JsonIgnore
	private final transient NodeLeader nodeLeader;
	
	@JsonIgnore
	private final transient ZookeeperExecutor executor;
	
	@JsonIgnore
	private final transient JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	@JsonIgnore
	private transient SimpleHttpNioChannelServer server;
	
	/**
	 * watcher on worker trigger path /  temporary
	 */
	@Deprecated
	private NodeCache workflowTriggerCache;
	
	/**
	 * watcher on  {@link CoordinatorPaths#BASE_PATH}/workflowadd
	 */
	@Deprecated
	private transient PathChildrenCache workfowAddCache;
	
	/**
	 * all running/ already run  instance 
	 */
	private LinkedHashMap<Long,Instance> instances=Maps.newLinkedHashMap();
	
	/**
	 * all workflows in the cluster
	 */
	private Map<String, Workflow> workflows=Maps.newConcurrentMap();
	
	private final LeaderNodeMeta leaderNodeMeta;
	
	@JsonIgnore
	private transient TaskRepo taskRepo;
	
	@JsonIgnore
	private transient WorkflowMetaRepo workflowMetaRepo;
	
	@JsonIgnore
	private static transient ScheduledExecutorService workflowStatusExecutorService=_workflowStatusExecutorService();

	@JsonIgnore
	private transient final InstanceCtl instanceCtl;
	
	private static ScheduledExecutorService _workflowStatusExecutorService() {
		return 
				Executors.newScheduledThreadPool(1,new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r,"workflow-offline-online-check");
					}
				});
	}
	
	@JsonIgnore
	private static transient ScheduledExecutorService workflowCheckExecutorService=
														_workflowCheckExecutorService();
	
	public static ScheduledExecutorService _workflowCheckExecutorService() {
		return Executors.newScheduledThreadPool(1,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"workflow-assemble-check");
			}
		});
	}
	
	@JsonIgnore
	private static transient ScheduledExecutorService workflowFollowerIfActiveCheckExecutorService=
			_workflowFollowerIfActiveCheckExecutorService();

	public static ScheduledExecutorService _workflowFollowerIfActiveCheckExecutorService() {
		return Executors.newScheduledThreadPool(1,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r,"leader-follower-if-active-check");
			}
		});
	}
	
	
	private Map<Class<?>,List<WorkflowCommand<?>>> workflowCommands
	=Maps.newConcurrentMap();
	
	public WorkflowMaster(NodeLeader nodeLeader,LeaderNodeMeta leaderNodeMeta) throws Exception {
		this.nodeLeader=nodeLeader;
		this.leaderNodeMeta=leaderNodeMeta;
		executor=nodeLeader.getExecutor();
		taskRepo=new ZKTaskRepo(executor, 
				leaderNodeMeta.getTaskRepoPath());
		instanceCtl=new InstanceCtl(nodeLeader, this);
		registerLeaderIsLeaderInZookeeper();
		registerRPCInZookeeper();
		startWorkflowStatusCheck(leaderNodeMeta);
//		startWorkflowAddWatcher();
		startWorfkowTriggerWatcher();
		startLeaderFollowerIfActive();
		addCommonCommand();
		startServer();
		workflowMetaRepo=_workflowMetaRepo(nodeLeader);
	}
	
	
	/**
	 * node leader code
	 * @return
	 */
	private synchronized LeaderNodeMeta registerLeaderIsLeaderInZookeeper(){
        byte[] msg=
        		SerializerUtils.serialize(serializerFactory, leaderNodeMeta);
        String realLeaderIsLeaderPath=NodeLeader.leaderIsLeaderRegisterPath();
		if(!executor.exists(realLeaderIsLeaderPath)){
			executor.createPath(realLeaderIsLeaderPath, msg);
		}else{
			executor.setPath(realLeaderIsLeaderPath, msg);
		}
		String realLeaderFollowerPath=nodeLeader.realLeaderFollowerPath();
		if(executor.exists(realLeaderFollowerPath)){
			executor.deletePath(realLeaderFollowerPath);
		}
		return leaderNodeMeta;
	}
	
	/**
	 * node leader code
	 */
	private void registerRPCInZookeeper(){
		String rpcNode=JConfiguration.get().getString(
				ConfigNames.STREAMING_LEADER_RPC_HOST_ZNODE);
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
	 * node leader  code
	 * @throws Exception
	 */
	private void startServer() throws Exception{
		server =
				new SimpleHttpNioChannelServer(leaderNodeMeta.getPort());
		server.start();
	}

	private void addCommonCommand(){
		addWorkflowCommand(new WorkflowCompleteCommand()) ;
		addWorkflowCommand(new WorkflowRetryCommand());
		addWorkflowCommand(new WorkflowErrorCommand());
	}
	
	private ZKWorkflowMetaRepo _workflowMetaRepo(NodeLeader nodeLeader) {
		return new ZKWorkflowMetaRepo(nodeLeader.getExecutor(), 
				NodeLeader.workflowAddPath(), new ChangedCallBack() {
					
					@Override
					public void removeable(List<WorkflowMeta> workflowMetas) {
						for (WorkflowMeta workflowMeta : workflowMetas) {
							if(workflowMeta!=null){
								try {
									_removeWorkflowMeta(workflowMeta);
								} catch (Exception e) {
									nodeLeader.logError(e);
								}
							}
						}
					}
					
					@Override
					public void addable(List<WorkflowMeta> workflowMetas) {
						for (WorkflowMeta workflowMeta : workflowMetas) {
							if(workflowMeta!=null){
								_addWorkflowMeta(workflowMeta);
							}
						}
					}
				});
	}
	
	synchronized void _removeWorkflowMeta(WorkflowMeta workflowMeta) throws Exception{
		Workflow workflow=getWorkflow(workflowMeta.getName());
		if(workflow!=null){
			try{
				workflow.setStop();
			}finally{
				workflow.close();
			}
			workflows.remove(workflow.getName());
		}
	}
	
	
	
	/**
	 * add workflows to current master instance, including attaching any watcher on worker registered path.
	 * @param workflowMeta
	 */
	synchronized void _addWorkflowMeta(WorkflowMeta workflowMeta){
//		if(!workflowMaster.existsWorkflow(workflowMeta.getName())){
			Workflow workflow=getWorkflow(workflowMeta.getName());
			if(workflow==null){
				workflow=new Workflow(workflowMeta.getName());
				workflow.setNodeData(workflowMeta.getNodeData());
				workflow.setWorkflowMeta(workflowMeta);
				addWorkflow(workflow);
				startWorkersPathWatcher(workflow);
			}
			else{
//				workflow.setNodeData(workflowMeta.getNodeData());
				throw new IllegalStateException("workflow["+workflowMeta.getName()+"] already exists,update this after uninstalling this");
			}
//		}
	}
	
	/**
	 * watcher on the path {@link #pluginWorkersPath(Workflow)} find all real workers in the workflow.
	 * @param workflow
	 */
	private void startWorkersPathWatcher(final Workflow workflow){
		if(workflow.getPluginWorkersPathCache()!=null) return ;
		String _path=workflow.pluginWorkersPath();
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
		}, nodeLeader.zooKeeperExecutorService(),
				PathChildrenCacheEvent.Type.CHILD_ADDED,
				PathChildrenCacheEvent.Type.CHILD_REMOVED);
		workflow.setPluginWorkersPathCache(cache);
	}
	
//	/**
//	 * all workflows should be registered in the {@link #workflowAddPath()} as a child node
//	 * in the zookeeper
//	 */
//	@Deprecated
//	private synchronized void startWorkflowAddWatcher(){
//		final String workflowAddPath=NodeLeader.workflowAddPath();
//		if(!executor.exists(workflowAddPath)){
//			executor.createPath(workflowAddPath);
//		}
//		PathChildrenCache cache=  executor.watchChildrenPath(workflowAddPath, 
//				new ZooNodeChildrenCallback() {
//			
//			@Override
//			public void call(List<ZooNode> nodes) {
//				for(ZooNode node:nodes){
//					byte[] bytes=node.getDataAsPossible(executor);
//					WorkflowMeta workflowMeta=
//							SerializerUtils.deserialize(serializerFactory, bytes, WorkflowMeta.class);
//					_addWorkflowMeta(workflowMeta);
//				}
//			}
//		} , Executors.newFixedThreadPool(1, new ThreadFactory() {
//			@Override
//			public Thread newThread(Runnable r) {
//				return new Thread(r, workflowAddPath+"{watcher workflow add}");
//			}
//		}), PathChildrenCacheEvent.Type.CHILD_ADDED
//				,PathChildrenCacheEvent.Type.CHILD_REMOVED
//				,PathChildrenCacheEvent.Type.CHILD_UPDATED);
//		this.workfowAddCache =(cache);
//	}
	
	
	/**
	 * a registering queue , workflow need be registered 
	 * in the node {@link #workflowTrigger(Workflow)}  in the zookeeper.
	 * @param workflow
	 * @see IWorkflowService#triggerWorkflow(String, Map)
	 */
	@Deprecated
	private void startWorfkowTriggerWatcher(){
		final String path=nodeLeader.workflowTrigger(null);
		if(!executor.exists(path)){
			executor.createPath(path);
		}
		NodeCache cache=executor.watchPath(path, new ZooNodeCallback() {
			@Override
			public void call(ZooNode node) {
				WorkflowMeta workflowMeta=
						SerializerUtils.deserialize(serializerFactory, node.getDataAsPossible(executor), WorkflowMeta.class);
				nodeLeader.startWorkflow(workflowMeta.getName(), Maps.newHashMap());
			}
		}, nodeLeader.zooKeeperExecutorService());
		this.workflowTriggerCache =(cache);
	}
	
	private int workerId(String path){
		return Integer.parseInt(path.substring(path.lastIndexOf("/")).split("-")[1]);
	}
	
	
	private void startLeaderFollowerIfActive(){
		if(workflowFollowerIfActiveCheckExecutorService.isShutdown()){
			workflowFollowerIfActiveCheckExecutorService=_workflowFollowerIfActiveCheckExecutorService();
		}
		workflowFollowerIfActiveCheckExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				final String leaderFollowerRegisterPath=NodeLeader.leaderFollowerRegisterPath();
				executor.getChildren(leaderFollowerRegisterPath).forEach(realRegisterPath->{
					String realPath=leaderFollowerRegisterPath
													+"/"+realRegisterPath;
					if(executor.getChildren(realPath).isEmpty()){
						nodeLeader.logInfo("follwer path["+realPath+"] is offline.");
						executor.deletePath(realPath);
					}
				});
			}
		}, 0, 10000, TimeUnit.MILLISECONDS);
	}

	private void startWorkflowWorkersIfActive() {
		if(workflowCheckExecutorService.isShutdown()){
			workflowCheckExecutorService=_workflowCheckExecutorService();
		}
		workflowCheckExecutorService.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				for(Workflow workflow:workflows.values()){
					checkWorkerIfActive(workflow);
				}
			}
		}, 0, 10000, TimeUnit.MILLISECONDS);
	}
	
	private boolean checkWorkerIfActive(Workflow workflow){
		boolean valid=false;
		try{
			Map<Integer, String> workerPaths=workflow.getWorkerPaths();
			Iterator<Entry<Integer, String>> iterator=workerPaths.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<Integer, String> entry=iterator.next();
				try{
					SingleMonitor singleMonitor=SingleMonitor.get(
							RestrictLockPath.workerLockPath(workflow.getName(), entry.getKey())); 
					try{
						singleMonitor.acquire();
						Integer workerId=entry.getKey();
						String processorPath=nodeLeader.pluginWorkerProcessorPath(workerId, workflow);
						if(!executor.exists(processorPath)){
							LOGGER.info(" worker["+workerId+"] is offline , remove it."); 
							iterator.remove();
						}
						List<String> chls=executor.getChildren(processorPath);
						boolean hasProcessor=false;
						for(String chPath:chls){
							String relProcessorPath=processorPath+"/"+chPath;
							if(!executor.getChildren(relProcessorPath).isEmpty()){
								hasProcessor=true;
							}else{
								//may delete the node (broken from the zookeeper)
								executor.deletePath(relProcessorPath);
							}
						}
						if(!hasProcessor){
							String pluginWorkerPath=nodeLeader.pluginWorkerPath(workerId, workflow);
							executor.deletePath(pluginWorkerPath);
							LOGGER.info(" worker["+workerId+"] is offline , remove it."); 
							iterator.remove();
						}
					}catch (Exception e) {
						nodeLeader.logError(e);
					}finally{
						try {
							singleMonitor.release();
						} catch (Exception e) {
							nodeLeader.logError(e);
						}
					}
				}catch (Exception e) {
					nodeLeader.logError(e);
				}
			}
			workerPaths=workflow.getWorkerPaths();
			List<Integer> workers=workflow.getNodeData().getWorkers();
			String missingWorkers="";
			for (Integer defWorkerId : workers) {
				if(!workerPaths.containsKey(defWorkerId)){
					missingWorkers=missingWorkers+","+defWorkerId;
				}
			}
			if(missingWorkers.length()>0){
				WorkflowErrorCode.E0002.clearCause();
				workflow.setError(WorkflowErrorCode.E0002.setThrowable(
						new IllegalStateException("workers["+missingWorkers+"] is offline")));
			}else{
				if(workflow.workflowCheck().isError()){
					if(workflow.containsError(WorkflowErrorCode.E0002)){
						LOGGER.info(nodeLeader.getMessage("attempt to recover workflow :"+workflow.getName()));
						workflow.removeError(WorkflowErrorCode.E0002,new SimpleCallBack() {
							@Override
							public void call(Object object) {
								notifyWorkflowStart(workflow, instanceCtl.getTaskCallBack());
							}
						});
						
					}
				}
				valid=true;
			}
		}catch (Exception e) {
			nodeLeader.logError(e);
		}
		return valid;
	}

	private void startWorkflowStatusCheck(LeaderNodeMeta leaderNodeMeta) {
		if(workflowStatusExecutorService.isShutdown()){
			workflowStatusExecutorService=_workflowStatusExecutorService();
		}
		workflowStatusExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try{
					for(Workflow workflow:workflows.values()){
						if(workflow.workflowCheck().isOffline()){
							long onlineTime=workflow.getOnlineStartTime();
							Date date=new Date();
							if(onlineTime==-1){
								workflow.setOnlineStartTime(date.getTime());
							}else{
								long interval=date.getTime()-onlineTime;
								if(interval>leaderNodeMeta.getWorkflowToOnlineMs()){
									workflow.setOnline();
									if(checkWorkerIfActive(workflow)){
										notifyWorkflowStart(workflow, instanceCtl.getTaskCallBack());
									}
									startWorkflowWorkersIfActive();
								}
							}
						}
					}
				}catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}, 0, leaderNodeMeta.getWorkflowStatusMs(),TimeUnit.MILLISECONDS);
	}
	
	
	
	@Override
	public void close() throws IOException {
		CloseException exception=new CloseException();
		if(workflowTriggerCache!=null){
			try {
				workflowTriggerCache.close();
			} catch (IOException e) {
				exception.addMessage(e.getMessage());
			}
		}
		if(workfowAddCache!=null){
			try {
				workfowAddCache.close();
			} catch (IOException e) {
				exception.addMessage(e.getMessage());
			}
		}
		for(Instance instance:instances.values()){
			try{
				instance.close();
			}catch (Exception e) {
				exception.addMessage(e.getMessage());
			}
		}
		instances.clear();
		
		for(Workflow workflow:workflows.values()){
			try{
				workflow.close();
			}catch (Exception e) {
				exception.addMessage(e.getMessage());
			}
		}
		workflows.clear();
		
		try{
			if(server!=null){
				server.close();
			}
		}catch (Exception e) {
			exception.addMessage(e.getMessage());
		}
		
		try{
			workflowCheckExecutorService.shutdownNow();
			workflowStatusExecutorService.shutdownNow();
			workflowFollowerIfActiveCheckExecutorService.shutdownNow();
		}catch (Exception e) {
			exception.addMessage(e.getMessage());
		}
		
		if(exception.has())
			throw exception;
	}
	
	public boolean existsWorkflow(String workflowName){
		return workflows.containsKey(workflowName);
	}
	
	public void addInstance(Long sequence,Instance instance){
		instances.put(sequence, instance);
	}
	
	public Instance getInstance(Long sequence){
		return instances.get(sequence);
	}
	

	private void addWorkflow(Workflow workflow){
		if(!existsWorkflow(workflow.getName())){
			workflows.put(workflow.getName(), workflow);
		}
	}

	public synchronized void addWorkflowCommand(WorkflowCommand<? extends WorkflowCommandModel> workflowCommand ){
		List<WorkflowCommand<?>> commands
			=workflowCommands.get(workflowCommand.getGenericClass());
		if(commands==null){
			commands=new ArrayList<>();
			workflowCommands.put(workflowCommand.getGenericClass(), commands);
		}
		commands.add(workflowCommand);
	}
	
	public Collection<WorkflowCommand> workflowCommands(Class<? extends WorkflowCommandModel> clazz) {
		Collection<WorkflowCommand<?>> commands=workflowCommands.get(clazz);
		if(commands==null) return Collections.EMPTY_LIST;
		return Collections.unmodifiableCollection(commands);
	}
	
	public NodeCache getWorkflowTriggerCache() {
		return workflowTriggerCache;
	}

	public void setWorkflowTriggerCache(NodeCache workflowTriggerCache) {
		this.workflowTriggerCache = workflowTriggerCache;
	}

	public Map<Long, Instance> getInstances() {
		return instances;
	}

	void setInstances(LinkedHashMap<Long, Instance> instances) {
		this.instances = instances;
	}

	public Map<String, Workflow> getWorkflows() {
		return workflows;
	}
	
	public Workflow getWorkflow(String name){
		return workflows.get(name);
	}

	public void setWorkflows(Map<String, Workflow> workflows) {
		this.workflows = workflows;
	}

	public LeaderNodeMeta getLeaderNodeMeta() {
		return leaderNodeMeta;
	}
	
	private void notifyWorkflowStart(Workflow workflow,TaskCallBack taskCallBack){
		String workflowName=workflow.getName();
		WorkflowCheck workflowCheck=workflow.workflowCheck();
		if(workflowCheck.isError()){
			int i=0;
			for(Throwable t:workflow.getStatus().getCause()){
				nodeLeader.logError(new IllegalStateException("workflow["+workflowName+"] error["+
									(++i)+"]; ")
						.initCause(t));
			}
		}
		if(workflowCheck.isOnline()
				&&!workflowCheck.isLock()){
			taskCallBack.call(taskRepo.getTaskByWorfklowName(workflowName),new SimpleCallBack() {
				@Override
				public void call(Object object) {
					if(object instanceof Throwable){
						nodeLeader.logError((Throwable) object);
						taskCallBack.call(taskRepo.getTaskByWorfklowName(workflowName), this);
					}
				}
			});
		}
	}
	
	public synchronized String addTask(Task task,TaskCallBack taskCallBack){
		String taskId=taskRepo.addTask(task);
		notifyWorkflowStart(getWorkflow(task.getWorkflowName()), taskCallBack);
		return taskId;
	}
	
	public synchronized void completeInstance(Long sequence,TaskCallBack taskCallBack){
		completeInstance(getInstance(sequence), taskCallBack);
	}
	
	public synchronized void completeInstance(Instance instance,TaskCallBack taskCallBack){
		String workflowName=instance.getWorkflow().getName();
		Workflow workflow=getWorkflow(workflowName);
		Long sequence=instance.getSequence();
		if(workflow.workflowCheck().tryLock(sequence)){
			workflow.workflowCheck().release(sequence);
			notifyWorkflowStart(workflow, taskCallBack);
		}else{
			throw new RuntimeException("workflow[locked:"+workflow.workflowCheck().getLockSequence()+"] is not locked by the instance : "+sequence);
		}
	}
	
	public boolean sendHeartbeats(ExecutingWorker executingWorker){
		long sequence=executingWorker.getSequence();
		Instance instance=getInstance(sequence);
		if(instance!=null){
			instance.sendHeartbeats(executingWorker);
		}
		return true;
	}
	
	public String addWorkflowMeta(WorkflowMeta workflowMeta){
		return workflowMetaRepo.addWorkflowMeta(workflowMeta);
	}
	
	public String removeWorkflowMeta(String workflowName){
		return workflowMetaRepo.removeWorkflowMeta(workflowName);
	}
	
	/**
	 * start workflow if possible
	 * @param name
	 * @param conf
	 */
	public void startWorkflow(String name,Map<String, Object> conf){
		Workflow workflow=getWorkflow(name);
		if(workflow==null){
			throw new RuntimeException("workflow is missing, to add workflow in the container.");
		}
		Task task=new Task();
		task.setWorkflowName(name);
		task.setParams(conf);
		addTask(task,instanceCtl.getTaskCallBack());
	}
	
	public Collection<Task> getTasks(){
		ZKTaskRepo _taskRepo=(ZKTaskRepo)taskRepo;
		return _taskRepo.getAllTasks();
	}
	
	public Task getTask(String unique){
		ZKTaskRepo _taskRepo=(ZKTaskRepo)taskRepo;
		return _taskRepo.getTask(unique);
	}
	
}
