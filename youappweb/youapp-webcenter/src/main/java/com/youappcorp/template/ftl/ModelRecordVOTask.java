package com.youappcorp.template.ftl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import freemarker.template.Template;
import me.bunny.kernel.taskdriven.tkdd.JTaskMetadataHierarchy;
import me.bunny.kernel.taskdriven.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class ModelRecordVOTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		ModelConfig modelConfig=getModelConfig();
		
		ModelModel modelModel=modelConfig.modelModel();
		
		ModelRecordVOModel modelRecordVOModel=new ModelRecordVOModel();
		modelRecordVOModel.setClassPackage(modelConfig.internalConfig().voPackage());
		modelRecordVOModel.setSimpleClassName(modelModel.getSimpleClassName()+"RecordVO");
		modelRecordVOModel.setClassName(modelRecordVOModel.getClassPackage()+"."
		+modelRecordVOModel.getSimpleClassName());
		modelConfig.setModelRecordVOModel(modelRecordVOModel);
		
		/* Create a data-model */
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("modelModel", modelModel);
        root.put("modelRecordModel", modelConfig.modelRecordModel());
        root.put("modelRecordVOModel", modelRecordVOModel);
        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("model-record-vo.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        String javaFileName=getInternalConfig().javaRelativePath()+"/"
        +modelRecordVOModel.getClassName().replace('.', '/')+".java";
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(javaFileName));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
        
        return true;
	}

}
