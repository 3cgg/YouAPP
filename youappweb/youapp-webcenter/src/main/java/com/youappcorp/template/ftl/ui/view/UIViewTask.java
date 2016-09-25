package com.youappcorp.template.ftl.ui.view;

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

import freemarker.template.Template;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class UIViewTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {

		ModelConfig modelConfig=getModelConfig();
		ModelModel modelModel=modelConfig.modelModel();
		
		UIViewModel uiViewModel=new UIViewModel();
		uiViewModel.setUiContext(modelConfig.uiTemplateUIContext());
		uiViewModel.setFilePath(uiViewModel.getUiContext().getViewFilePath());
		uiViewModel.setFileName(uiViewModel.getUiContext().getViewFileName());
		
		UIViewFieldParser viewFieldParser=new UIDefaultViewFieldParser();
		uiViewModel.setViewFields(viewFieldParser.parse(modelConfig));

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
        root.put("uiViewModel", uiViewModel);
        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("ui/ui-model-view.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(uiViewModel.getUiContext().getUiRelativePath()
        		+uiViewModel.getFilePath()));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
	
        return true;
        
	}

}
