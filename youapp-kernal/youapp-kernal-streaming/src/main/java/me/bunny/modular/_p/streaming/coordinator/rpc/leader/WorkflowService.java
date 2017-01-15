package me.bunny.modular._p.streaming.coordinator.rpc.leader;

import java.util.Collection;
import java.util.Map;

import me.bunny.kernel._c.serializer.JSerializerFactory;
import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.modular._p.streaming.coordinator.Instance;
import me.bunny.modular._p.streaming.coordinator.NodeLeader;
import me.bunny.modular._p.streaming.coordinator.Task;
import me.bunny.modular._p.streaming.coordinator.Workflow;
import me.bunny.modular._p.streaming.coordinator.WorkflowMaster;
import me.bunny.modular._p.streaming.coordinator.WorkflowMeta;
import me.bunny.modular._p.streaming.coordinator._SerializeFactoryGetter;
import me.bunny.modular._p.streaming.netty.controller.ControllerSupport;
import me.bunny.modular._p.streaming.netty.controller.JRequestMapping;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperExecutorGetter;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

@JRequestMapping(path="/workflowservice")
public class WorkflowService extends ControllerSupport<WorkflowService>
implements IWorkflowService{

	private JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();
	
	private ZookeeperExecutor executor=ZooKeeperExecutorGetter.getDefault();
	
	@Override
	public String getControllerServiceName() {
		return "WorkflowService";
	}

	@Override
	public boolean addWorkflow(WorkflowMeta workflowMeta) {
		JAssert.isNotEmpty(workflowMeta.getName());
		String unique=NodeLeader.runtime().addWorkflowMeta(workflowMeta);
		return JStringUtils.isNotNullOrEmpty(unique);
	}

	@Override
	public boolean removeWorkflow(String name) {
		JAssert.isNotEmpty(name);
		NodeLeader.runtime().removeWorkflowMeta(name);
		return true;
	}

	@Override
	public boolean triggerWorkflow(String name,Map<String, Object> conf) {
		NodeLeader.runtime().startWorkflow(name, conf);
		return false;
	}
	
	@Override
	public boolean sendHeartbeats(ExecutingWorker executingWorker) {
		return NodeLeader.runtime().sendHeartbeats(executingWorker);
	}
	
	public Collection<String> getWorkflows(){
		return NodeLeader.runtime().workflowMaster().getWorkflows().keySet();
	}
	
	public Workflow getWorkflow(String name){
		return NodeLeader.runtime().workflowMaster().getWorkflow(name);
	}
	
	public Instance getInstance(Long sequence){
		WorkflowMaster workflowMaster=NodeLeader.runtime().workflowMaster();
		return workflowMaster.getInstance(sequence);
	}
	
	public Collection<Long> getInstances(){
		WorkflowMaster workflowMaster=NodeLeader.runtime().workflowMaster();
		return workflowMaster.getInstances().keySet();
	}

	public Collection<Task> getTasks(){
		WorkflowMaster workflowMaster=NodeLeader.runtime().workflowMaster();
		return workflowMaster.getTasks();
	}
	
	public Task getTask(String unique){
		WorkflowMaster workflowMaster=NodeLeader.runtime().workflowMaster();
		return workflowMaster.getTask(unique);
	}
	
}
