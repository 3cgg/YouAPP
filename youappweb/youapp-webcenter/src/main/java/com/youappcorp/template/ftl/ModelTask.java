package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class ModelTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		ModelConfig modelConfig=getModelConfig();
		
		ModelModel modelModel=new ModelModel();
	
		modelModel.setModelSimpleClassName(modelConfig.modelName());
		modelModel.setModelPackage(modelConfig.internalConfig().modelPackage());
		modelModel.setModelClassName(modelModel.getModelPackage()+"."+modelModel.getModelSimpleClassName());
		modelConfig.setModelModel(modelModel);
		
        return true;
        
	}

}
