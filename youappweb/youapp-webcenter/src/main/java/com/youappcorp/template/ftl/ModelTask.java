package com.youappcorp.template.ftl;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class ModelTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		ModelConfig modelConfig=getModelConfig();
		
		ModelModel modelModel=new ModelModel();
	
		modelModel.setSimpleClassName(modelConfig.modelName());
		modelModel.setClassPackage(modelConfig.internalConfig().modelPackage());
		modelModel.setClassName(modelModel.getClassPackage()+"."
		+modelModel.getSimpleClassName());
		modelConfig.setModelModel(modelModel);
		
		ModelFieldParser modelFieldParser=(ModelFieldParser) JClassUtils.newObject(getConfig().getModelFieldParserClass());
		List<ModelField> modelFields= modelFieldParser.parse(JClassUtils.load(modelModel.getClassName()));
		modelModel.setModelFields(modelFields);
		
		
		
        return true;
        
	}

}
