package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JBaseTask;
import j.jave.kernal.taskdriven.tkdd.JTaskExecutionException;

public abstract class TemplateTask extends JBaseTask {

	
	protected InternalConfig getInternalConfig(){
		return TemplateUtil.getInternalConfig(getTaskContext().getFlowContext());
	}
	
	protected void setInternalConfig(InternalConfig internalConfig){
		TemplateUtil.setInternalConfig(getTaskContext().getFlowContext(), internalConfig);
	}
	
	protected Config getConfig(){
		return TemplateUtil.getConfig(getTaskContext().getFlowContext());
	}
	
	@Override
	public final Object run() throws JTaskExecutionException {
		try{
			return doRun();
		}catch(Exception e){
			throw new JTaskExecutionException(e);
		}
	}
	
	protected abstract Object doRun() throws Exception;

}
