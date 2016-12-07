package j.jave.kernal.streaming.coordinator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class InstanceCtl {

	private final NodeLeader nodeLeader;
	
	private final WorkflowMaster workflowMaster;
	
	@JsonIgnore
	private final transient ZookeeperExecutor executor;
	
	@JsonIgnore
	private final transient JSerializerFactory serializerFactory=_SerializeFactoryGetter.get();

	public InstanceCtl(NodeLeader nodeLeader, WorkflowMaster workflowMaster) {
		this.nodeLeader = nodeLeader;
		this.workflowMaster = workflowMaster;
		this.executor=nodeLeader.getExecutor();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
