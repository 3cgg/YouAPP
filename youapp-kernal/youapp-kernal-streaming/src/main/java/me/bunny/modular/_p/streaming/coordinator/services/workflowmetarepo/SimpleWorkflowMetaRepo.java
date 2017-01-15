package me.bunny.modular._p.streaming.coordinator.services.workflowmetarepo;

import java.util.Arrays;
import java.util.List;

import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.modular._p.streaming.coordinator.WorkflowMeta;

public abstract class SimpleWorkflowMetaRepo implements WorkflowMetaRepo {
	
	protected final ChangedCallBack changedCallBack;
	
	public SimpleWorkflowMetaRepo(ChangedCallBack changedCallBack) {
		this.changedCallBack = changedCallBack;
	}

	@Override
	public synchronized String addWorkflowMeta(WorkflowMeta workflowMeta) {
		String unique= _addWorkflowMeta(workflowMeta);
		if(JStringUtils.isNotNullOrEmpty(unique)){
			notifyAdded(Arrays.asList(workflowMeta));
		}
		return unique;
	}

	
	/**
	 * 
	 * @param workflowMeta
	 * @return the worklfow unique {@link WorkflowMeta#getUnique()} if add success
	 */
	abstract protected String _addWorkflowMeta(WorkflowMeta workflowMeta);
	
	@Override
	public synchronized String removeWorkflowMeta(String workflowName) {
		WorkflowMeta workflowMeta= _removeWorkflowMeta(workflowName);
		if(workflowMeta!=null){
			notifyRemoved(Arrays.asList(workflowMeta));
		}
		return workflowMeta.getUnique();
	}

	/**
	 * 
	 * @param workflowName
	 * @return the workflow meta of the name if remove successfully
	 */
	abstract protected WorkflowMeta _removeWorkflowMeta(String workflowName);
	
	protected void notifyAdded(List<WorkflowMeta> workflowMetas){
		if(changedCallBack!=null){
			changedCallBack.addable(workflowMetas);
		}
	}
	
	protected void notifyRemoved(List<WorkflowMeta> workflowMetas){
		if(changedCallBack!=null){
			changedCallBack.removeable(workflowMetas);
		}
	}
	
}
