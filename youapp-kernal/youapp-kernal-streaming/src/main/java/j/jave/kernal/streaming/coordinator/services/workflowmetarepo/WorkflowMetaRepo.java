package j.jave.kernal.streaming.coordinator.services.workflowmetarepo;

import j.jave.kernal.streaming.coordinator.WorkflowMeta;

public interface WorkflowMetaRepo {

	String addWorkflowMeta(WorkflowMeta workflowMeta);
	
	String removeWorkflowMeta(String workflowName);
	
	
	
}
