package me.bunny.modular._p.streaming.coordinator.services.workflowmetarepo;

import me.bunny.modular._p.streaming.coordinator.WorkflowMeta;

public interface WorkflowMetaRepo {

	String addWorkflowMeta(WorkflowMeta workflowMeta);
	
	String removeWorkflowMeta(String workflowName);
	
	
	
}
