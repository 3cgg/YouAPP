package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import freemarker.template.Template;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class ServiceTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		List<Map<String, Object>> models=new ArrayList<Map<String,Object>>();
		ServiceModel tempServiceModel=null;
		for(ModelConfig modelConfig:getInternalConfig()){
			ModelModel modelModel=modelConfig.modelModel();
			
			ServiceModel serviceModel=new ServiceModel();
			serviceModel.setClassPackage(modelConfig.internalConfig().servicePackage());
			serviceModel.setSimpleClassName(getConfig().getModuleName()+"Service");
			serviceModel.setClassName(serviceModel.getClassPackage()+"."
			+serviceModel.getSimpleClassName());
			serviceModel.setSaveMethodName(modelConfig.saveMethodName());
			serviceModel.setDeleteMethodName(modelConfig.deleteMethodName());
			serviceModel.setDeleteByIdMethodName(modelConfig.deleteByIdMethodName());
			serviceModel.setUpdateMethodName(modelConfig.updateMethodName());
			serviceModel.setGetMethodName(modelConfig.getMethodName());
			serviceModel.setPageMethodName(modelConfig.pageMethodName());
//			serviceModel.setVariableName(TemplateUtil.variableName(serviceModel.getSimpleClassName()));
			modelConfig.setServiceModel(serviceModel);
			
			/* Create a data-model */
	        Map<String,Object> root = new HashMap<String, Object>();
	        root.put("repoModel", modelConfig.repoModel());
	        root.put("modelModel", modelModel);
	        root.put("internalServiceModel", modelConfig.internalServiceModel());
	        root.put("modelRecordModel", modelConfig.modelRecordModel());
	        root.put("criteriaModel", modelConfig.criteriaModel());
	        root.put("serviceModel", serviceModel);

	        models.add(root);
	        tempServiceModel=serviceModel;
		}
		
		/* Create a data-model */
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("models", models);
        root.put("classPackage", tempServiceModel.getClassPackage());
        root.put("className", tempServiceModel.getClassName());
        root.put("simpleClassName", tempServiceModel.getSimpleClassName());
        root.put("variableName", tempServiceModel.getVariableName());

        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("service-interface.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        String javaFileName=getInternalConfig().javaRelativePath()+"/"
        +tempServiceModel.getClassName().replace('.', '/')+".java";
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(javaFileName));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
	
        return true;
        
	}

}
