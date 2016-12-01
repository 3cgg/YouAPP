package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.google.common.collect.Maps;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand;
import j.jave.kernal.streaming.coordinator.command.WorkflowCommand.WorkflowCommandModel;

public class WorkflowMaster implements JModel ,Closeable{

	/**
	 * watcher on worker trigger path /  temporary
	 */
	@Deprecated
	private NodeCache workflowTriggerCache;
	
	/**
	 * watcher on  {@link CoordinatorPaths#BASE_PATH}/workflowadd
	 */
	private PathChildrenCache workfowAddCache;
	
	/**
	 * all running/ already run  instance 
	 */
	private Map<Long,Instance> instances=Maps.newConcurrentMap();
	
	/**
	 * all workflows in the cluster
	 */
	private Map<String, Workflow> workflows=Maps.newConcurrentMap();
	
	private LeaderNodeMeta leaderNodeMeta;

	private Map<Class<?>,List<WorkflowCommand<?>>> workflowCommands
	=Maps.newConcurrentMap();
	
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
	

	public void addWorkflow(Workflow workflow){
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

	public void setInstances(Map<Long, Instance> instances) {
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

	public PathChildrenCache getWorkfowAddCache() {
		return workfowAddCache;
	}

	public void setWorkfowAddCache(PathChildrenCache workfowAddCache) {
		this.workfowAddCache = workfowAddCache;
	}

	public LeaderNodeMeta getLeaderNodeMeta() {
		return leaderNodeMeta;
	}

	public void setLeaderNodeMeta(LeaderNodeMeta leaderNodeMeta) {
		this.leaderNodeMeta = leaderNodeMeta;
	}
	
	
}
