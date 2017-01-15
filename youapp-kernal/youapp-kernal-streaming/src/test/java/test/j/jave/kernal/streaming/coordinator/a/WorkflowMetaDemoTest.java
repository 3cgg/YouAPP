package test.j.jave.kernal.streaming.coordinator.a;

import me.bunny.modular._p.streaming.coordinator.DefaultNodeDataGenerator;
import me.bunny.modular._p.streaming.coordinator.WorkflowMeta;

public class WorkflowMetaDemoTest {

	public static WorkflowMeta get(){
		WorkflowMeta workflowMeta=new WorkflowMeta();
		workflowMeta.setName("default");
		workflowMeta.setCount(Long.MAX_VALUE);
		workflowMeta.setNodeData(DefaultNodeDataGenerator.INSTANCE.generate("default", null));
		return workflowMeta;
	}
	
}
