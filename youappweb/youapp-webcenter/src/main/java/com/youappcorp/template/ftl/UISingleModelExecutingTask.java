package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;
import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;
import j.jave.kernal.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class UISingleModelExecutingTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		for(ModelConfig modelConfig:getInternalConfig()){
			JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
			
			UIPreparedConfigTask uiPreparedConfigTask=new UIPreparedConfigTask();
			simpleLinkedFlowImpl.put(uiPreparedConfigTask);
			
			UIListTask uiListTask=new UIListTask();
			simpleLinkedFlowImpl.put(uiListTask);
			
			JFlowContext flowContext=getFlowContext();
			simpleLinkedFlowImpl.start(flowContext);
		}
        return true;
	}

}
