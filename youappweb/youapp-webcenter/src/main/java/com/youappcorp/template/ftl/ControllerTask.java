package com.youappcorp.template.ftl;

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
import me.bunny.kernel.taskdriven.tkdd.JTaskMetadataHierarchy;
import me.bunny.kernel.taskdriven.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class ControllerTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		List<Map<String, Object>> models=new ArrayList<Map<String,Object>>();
		ControllerModel tempControllerModel=null;
		for(ModelConfig modelConfig:getInternalConfig()){
			ModelModel modelModel=modelConfig.modelModel();
			
			ControllerModel controllerModel=new ControllerModel();
			controllerModel.setClassPackage(modelConfig.internalConfig().controllerPackage());
			controllerModel.setSimpleClassName(getConfig().getModuleName()+"Controller");
			controllerModel.setClassName(controllerModel.getClassPackage()+"."
			+controllerModel.getSimpleClassName());
			controllerModel.setControllerBaseMapping(modelConfig.internalConfig().controllerBaseMapping());
			controllerModel.setSaveMethodName(modelConfig.saveMethodName());
			controllerModel.setDeleteMethodName(modelConfig.deleteMethodName());
			controllerModel.setDeleteByIdMethodName(modelConfig.deleteByIdMethodName());
			controllerModel.setUpdateMethodName(modelConfig.updateMethodName());
			controllerModel.setGetMethodName(modelConfig.getMethodName());
			controllerModel.setPageMethodName(modelConfig.pageMethodName());
//			serviceModel.setVariableName(TemplateUtil.variableName(serviceModel.getSimpleClassName()));
			modelConfig.setControllerModel(controllerModel);
			
			/* Create a data-model */
	        Map<String,Object> root = new HashMap<String, Object>();
	        root.put("repoModel", modelConfig.repoModel());
	        root.put("modelModel", modelModel);
	        root.put("internalServiceModel", modelConfig.internalServiceModel());
	        root.put("modelRecordModel", modelConfig.modelRecordModel());
	        root.put("criteriaModel", modelConfig.criteriaModel());
	        root.put("serviceModel", modelConfig.serviceModel());
	        root.put("modelRecordVOModel", modelConfig.modelRecordVOModel());
	        root.put("controllerModel", controllerModel);
	        
	        models.add(root);
	        tempControllerModel=controllerModel;
		}
		
		/* Create a data-model */
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("models", models);
        root.put("classPackage", tempControllerModel.getClassPackage());
        root.put("className", tempControllerModel.getClassName());
        root.put("simpleClassName", tempControllerModel.getSimpleClassName());
        root.put("variableName", tempControllerModel.getVariableName());
        root.put("controllerBaseMapping", tempControllerModel.getControllerBaseMapping());
        
        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("controller.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        String javaFileName=getInternalConfig().javaRelativePath()+"/"
        +tempControllerModel.getClassName().replace('.', '/')+".java";
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(javaFileName));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
	
        return true;
        
	}

}
