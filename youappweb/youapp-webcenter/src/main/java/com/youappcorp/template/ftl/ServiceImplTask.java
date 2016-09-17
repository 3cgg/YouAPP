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
public class ServiceImplTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		List<Map<String, Object>> models=new ArrayList<Map<String,Object>>();
		String interfaceClassName="";
		String simpleInterfaceClassName="";
		ServiceImplModel tempServiceImplModel=null;
		for(ModelConfig modelConfig:getInternalConfig()){
			ModelModel modelModel=modelConfig.modelModel();
			
			ServiceImplModel serviceImplModel=new ServiceImplModel();
			serviceImplModel.setClassPackage(modelConfig.internalConfig().servicePackage());
			serviceImplModel.setSimpleClassName(modelConfig.serviceModel().getSimpleClassName()+"Impl");
			serviceImplModel.setClassName(serviceImplModel.getClassPackage()+"."
			+serviceImplModel.getSimpleClassName());
//			serviceImplModel.setVariableName(TemplateUtil.variableName(serviceImplModel.getSimpleClassName()));
			modelConfig.setServiceImplModel(serviceImplModel);
			
			/* Create a data-model */
	        Map<String,Object> root = new HashMap<String, Object>();
	        root.put("repoModel", modelConfig.repoModel());
	        root.put("modelModel", modelModel);
	        root.put("internalServiceModel", modelConfig.internalServiceModel());
	        root.put("modelRecordModel", modelConfig.modelRecordModel());
	        root.put("criteriaModel", modelConfig.criteriaModel());
	        root.put("serviceModel", modelConfig.serviceModel());
	        root.put("serviceImplModel", modelConfig.serviceImplModel());
	        
	        models.add(root);
	        
	        interfaceClassName=modelConfig.serviceModel().getClassName();
	        simpleInterfaceClassName=modelConfig.serviceModel().getSimpleClassName();
	        tempServiceImplModel=serviceImplModel;
		}
		
		/* Create a data-model */
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("models", models);
        root.put("classPackage", tempServiceImplModel.getClassPackage());
        root.put("className", tempServiceImplModel.getClassName());
        root.put("simpleClassName", tempServiceImplModel.getSimpleClassName());
        
        root.put("interfaceClassName", interfaceClassName);
        root.put("simpleInterfaceClassName", simpleInterfaceClassName);
        
        root.put("variableName", tempServiceImplModel.getVariableName());

        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("service-interface-impl.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        String javaFileName=getInternalConfig().javaRelativePath()+"/"
        +tempServiceImplModel.getClassName().replace('.', '/')+".java";
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(javaFileName));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
	
        return true;
        
	}

}
