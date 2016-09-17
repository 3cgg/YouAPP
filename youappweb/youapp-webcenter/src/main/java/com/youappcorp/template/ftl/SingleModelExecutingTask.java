package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;
import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;
import j.jave.kernal.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class SingleModelExecutingTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		for(ModelConfig modelConfig:getInternalConfig()){
			JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
			
			ModelTask modelTask=new ModelTask();
			simpleLinkedFlowImpl.put(modelTask);
			
			RepoTask repoTask= new RepoTask();
			simpleLinkedFlowImpl.put(repoTask);
			
			InternalServiceTask internalServiceTask=new InternalServiceTask();
			simpleLinkedFlowImpl.put(internalServiceTask);
			
			ModelRecordTask modelRecordTask=new ModelRecordTask();
			simpleLinkedFlowImpl.put(modelRecordTask);
			
			ModelRecordVOTask modelRecordVOTask=new ModelRecordVOTask();
			simpleLinkedFlowImpl.put(modelRecordVOTask);
			
			CriteriaTask criteriaTask=new CriteriaTask();
			simpleLinkedFlowImpl.put(criteriaTask);
			
			JFlowContext flowContext=getFlowContext();
			TemplateUtil.setModelConfig(flowContext, modelConfig);
			
			
			simpleLinkedFlowImpl.start(flowContext);
		}
        return true;
	}

}
