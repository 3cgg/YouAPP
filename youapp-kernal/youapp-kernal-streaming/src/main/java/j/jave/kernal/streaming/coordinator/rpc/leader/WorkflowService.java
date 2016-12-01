package j.jave.kernal.streaming.coordinator.rpc.leader;

import java.util.Map;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.streaming.coordinator.NodeLeader;
import j.jave.kernal.streaming.coordinator.WorkflowMeta;
import j.jave.kernal.streaming.coordinator._SerializeFactoryGetter;
import j.jave.kernal.streaming.netty.controller.ControllerSupport;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;

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
		final String workflowAddPath=NodeLeader.workflowAddPath();
		String tempPath=workflowAddPath+"/"+workflowMeta.getName();
		if(executor.exists(tempPath)){
			return false;
		}
		executor.createPath(tempPath, 
				SerializerUtils.serialize(serializerFactory, workflowMeta));
		return true;
	}

	@Override
	public boolean removeWorkflow(String name) {
		JAssert.isNotEmpty(name);
		final String workflowAddPath=NodeLeader.workflowAddPath();
		String tempPath=workflowAddPath+"/"+name;
		executor.deletePath(tempPath);
		return true;
	}

	@Override
	public boolean triggerWorkflow(String name,Map<String, Object> conf) {
		NodeLeader.runtime().startWorkflow(name, conf);
		return false;
	}
	

}
