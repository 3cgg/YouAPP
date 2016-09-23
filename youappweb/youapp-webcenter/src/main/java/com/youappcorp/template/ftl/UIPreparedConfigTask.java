
package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class UIPreparedConfigTask extends TemplateTask {

	@Override
	protected Object doRun() throws Exception {
		
		ModelConfig modelConfig=getModelConfig();
		UITemplateUIContext uiTemplateUIContext=new UITemplateUIContext();
		String modelFileName=modelConfig.modelModel().getSimpleClassName().toLowerCase();
		uiTemplateUIContext.setListFileName(modelFileName+"-list.html");
		uiTemplateUIContext.setAddFileName(modelFileName+"-add.html");
		uiTemplateUIContext.setEditFilePath(modelFileName+"-edit.html");
		uiTemplateUIContext.setViewFileName(modelFileName+"-view.html");
		
		ControllerModel controllerModel=modelConfig.controllerModel();
		uiTemplateUIContext.setGetMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getGetMethodName());
		uiTemplateUIContext.setPageMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getPageMethodName());
		uiTemplateUIContext.setDeleteByIdMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getDeleteByIdMethodName());
		uiTemplateUIContext.setSaveMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getSaveMethodName());
		uiTemplateUIContext.setUpdateMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getUpdateMethodName());
		
		uiTemplateUIContext.setModuleName(getConfig().getModuleName());
		
		uiTemplateUIContext.setUiRelativePath(getConfig().getUiRelativePath());
		
		modelConfig.setUITemplateUIContext(uiTemplateUIContext);
		
		return true;
	}

}
