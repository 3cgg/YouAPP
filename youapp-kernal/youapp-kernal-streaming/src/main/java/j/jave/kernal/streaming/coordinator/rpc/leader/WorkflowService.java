package j.jave.kernal.streaming.coordinator.rpc.leader;

import java.util.Collection;
import java.util.Map;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.streaming.coordinator.Instance;
import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.coordinator.Workflow;
import j.jave.kernal.streaming.coordinator.WorkflowMaster;
import j.jave.kernal.streaming.coordinator.WorkflowMeta;
import j.jave.kernal.streaming.coordinator._SerializeFactoryGetter;
import j.jave.kernal.streaming.netty.controller.ControllerSupport;
import j.jave.kernal.streaming.netty.controller.JRequestMapping;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;

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

}
