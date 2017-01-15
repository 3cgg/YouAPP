package com.youappcorp.template.ftl.ui.edit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.youappcorp.template.ftl.FileWrapper;
import com.youappcorp.template.ftl.FtlConfig;
import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ModelModel;
import com.youappcorp.template.ftl.TemplateTask;
import com.youappcorp.template.ftl.ui.UIHiddenFieldParser;

import freemarker.template.Template;
import me.bunny.modular._p.taskdriven.tkdd.JTaskMetadataHierarchy;
import me.bunny.modular._p.taskdriven.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class UIEditTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {

		ModelConfig modelConfig=getModelConfig();
		ModelModel modelModel=modelConfig.modelModel();
		
		UIEditModel uiEditModel=new UIEditModel();
		uiEditModel.setUiContext(modelConfig.uiTemplateUIContext());
		uiEditModel.setFilePath(uiEditModel.getUiContext().getEditFilePath());
		uiEditModel.setFileName(uiEditModel.getUiContext().getEditFileName());
		
		UIEditFieldParser editFieldParser=new UIDefaultEditFieldParser();
		uiEditModel.setEditFields(editFieldParser.parse(modelConfig));

		UIHiddenFieldParser hiddenFieldParser=new UIDefaultEditHidenFieldParser();
		uiEditModel.setHiddenFields(hiddenFieldParser.parse(modelConfig));
		
		/* Create a data-model */
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("repoModel", modelConfig.repoModel());
        root.put("modelModel", modelModel);
        root.put("internalServiceModel", modelConfig.internalServiceModel());
        root.put("modelRecordModel", modelConfig.modelRecordModel());
        root.put("criteriaModel", modelConfig.criteriaModel());
        root.put("serviceModel", modelConfig.serviceModel());
        root.put("modelRecordVOModel", modelConfig.modelRecordVOModel());
        root.put("controllerModel", modelConfig.controllerModel());
        root.put("uiEditModel", uiEditModel);
        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("ui/ui-model-edit.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(uiEditModel.getUiContext().getUiRelativePath()
        		+uiEditModel.getFilePath()));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
	
        return true;
        
	}

}
