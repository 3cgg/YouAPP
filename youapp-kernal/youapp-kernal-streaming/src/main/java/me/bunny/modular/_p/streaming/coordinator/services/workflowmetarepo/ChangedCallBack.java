package me.bunny.modular._p.streaming.coordinator.services.workflowmetarepo;

import java.util.List;

import me.bunny.modular._p.streaming.coordinator.WorkflowMeta;

public interface ChangedCallBack {

	void addable(List<WorkflowMeta> workflowMetas);
	
	void removeable(List<WorkflowMeta> workflowMetas);
	
}
