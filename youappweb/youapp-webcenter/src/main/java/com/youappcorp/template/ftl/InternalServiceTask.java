package com.youappcorp.template.ftl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import freemarker.template.Template;
import me.bunny.modular._p.taskdriven.tkdd.JTaskMetadataHierarchy;
import me.bunny.modular._p.taskdriven.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class InternalServiceTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		ModelConfig modelConfig=getModelConfig();
		
		ModelModel modelModel=modelConfig.modelModel();
		
		InternalServiceModel internalServiceModel=new InternalServiceModel();
		internalServiceModel.setClassPackage(modelConfig.internalConfig().servicePackage());
		internalServiceModel.setSimpleClassName("Internal"+modelModel.getSimpleClassName()+"ServiceImpl");
		internalServiceModel.setClassName(internalServiceModel.getClassPackage()+"."
		+internalServiceModel.getSimpleClassName());
		modelConfig.setInternalServiceModel(internalServiceModel);
		
		/* Create a data-model */
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("repoModel", modelConfig.repoModel());
        root.put("modelModel", modelModel);
        root.put("internalServiceModel", internalServiceModel);
        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("internal-service.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        String javaFileName=getInternalConfig().javaRelativePath()+"/"
        +internalServiceModel.getClassName().replace('.', '/')+".java";
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(javaFileName));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
	
        return true;
        
	}

}
