
package com.youappcorp.template.ftl.ui;

import com.youappcorp.template.ftl.ControllerModel;
import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import me.bunny.kernel.taskdriven.tkdd.JTaskMetadataHierarchy;
import me.bunny.kernel.taskdriven.tkdd.JTaskMetadataOnTask;

import com.youappcorp.template.ftl.TemplateTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class UIPreparedConfigTask extends TemplateTask {

	@Override
	protected Object doRun() throws Exception {
		
		ModelConfig modelConfig=getModelConfig();
		UITemplateUIContext uiTemplateUIContext=new UITemplateUIContext();
		
		uiTemplateUIContext.setModuleName(getConfig().getModuleName().toLowerCase());
		uiTemplateUIContext.setUiRelativePath(getConfig().getUiRelativePath());
		
		uiTemplateUIContext.setUiRelativeMvcPath("/ui/pages");
		String modelFileName=modelConfig.modelModel().getSimpleClassName().toLowerCase();
		uiTemplateUIContext.setListFileName(modelFileName+"-list.html");
		uiTemplateUIContext.setListFilePath(uiTemplateUIContext.getUiRelativeMvcPath()
				+"/"+uiTemplateUIContext.getModuleName()
				+"/"+uiTemplateUIContext.getListFileName());
		
		uiTemplateUIContext.setAddFileName(modelFileName+"-add.html");
		uiTemplateUIContext.setAddFilePath(uiTemplateUIContext.getUiRelativeMvcPath()
				+"/"+uiTemplateUIContext.getModuleName()
				+"/"+uiTemplateUIContext.getAddFileName());
		
		uiTemplateUIContext.setEditFileName(modelFileName+"-edit.html");
		uiTemplateUIContext.setEditFilePath(uiTemplateUIContext.getUiRelativeMvcPath()
				+"/"+uiTemplateUIContext.getModuleName()
				+"/"+uiTemplateUIContext.getEditFileName());
		
		uiTemplateUIContext.setViewFileName(modelFileName+"-view.html");
		uiTemplateUIContext.setViewFilePath(uiTemplateUIContext.getUiRelativeMvcPath()
				+"/"+uiTemplateUIContext.getModuleName()
				+"/"+uiTemplateUIContext.getViewFileName());
		
		ControllerModel controllerModel=modelConfig.controllerModel();
		uiTemplateUIContext.setGetMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getGetMethodName());
		uiTemplateUIContext.setPageMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getPageMethodName());
		uiTemplateUIContext.setDeleteByIdMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getDeleteByIdMethodName());
		uiTemplateUIContext.setDeleteMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getDeleteMethodName());
		
		uiTemplateUIContext.setSaveMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getSaveMethodName());
		uiTemplateUIContext.setUpdateMethodUrl(controllerModel.getControllerBaseMapping()
				+"/"+controllerModel.getUpdateMethodName());
		
		
		
		modelConfig.setUITemplateUIContext(uiTemplateUIContext);
		
		return true;
	}

}
