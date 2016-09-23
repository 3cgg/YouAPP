package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import freemarker.template.Template;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class UIListTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {

		ModelConfig modelConfig=getModelConfig();
		ModelModel modelModel=modelConfig.modelModel();
		
		UIListModel uiListModel=new UIListModel();
		uiListModel.setUiConext(modelConfig.uiTemplateUIContext());
		uiListModel.setFilePath(uiListModel.getUiConext().getListFilePath());
		uiListModel.setFileName(uiListModel.getUiConext().getListFileName());
		uiListModel.setCheckbox(true);
		UIListCriterialFieldParser listCriterialFieldParser=new UIDefaultListCriterialFieldParser();
		uiListModel.setCriteriaFields(listCriterialFieldParser.parse(modelConfig));

		UIListTableFieldParser tableFieldParser=new UIDefaultListTableFieldParser();
		uiListModel.setTableFields(tableFieldParser.parse(modelConfig));

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
        root.put("uiListModel", uiListModel);
        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("ui-model-list.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(uiListModel.getFilePath()));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
	
        return true;
        
	}

}
