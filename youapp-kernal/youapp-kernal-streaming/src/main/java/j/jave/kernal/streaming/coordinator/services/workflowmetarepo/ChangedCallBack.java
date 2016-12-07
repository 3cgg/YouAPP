package j.jave.kernal.streaming.coordinator.services.workflowmetarepo;

import java.util.List;

import j.jave.kernal.streaming.coordinator.WorkflowMeta;

public interface ChangedCallBack {

	void addable(List<WorkflowMeta> workflowMetas);
	
	void removeable(List<WorkflowMeta> workflowMetas);
	
}
