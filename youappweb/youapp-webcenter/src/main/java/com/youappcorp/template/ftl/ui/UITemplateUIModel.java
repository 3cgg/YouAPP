package com.youappcorp.template.ftl.ui;

import j.jave.kernal.jave.model.JModel;

public class UITemplateUIModel implements JModel{
	
	private String filePath;
	
	private String fileName;
	
	private UITemplateUIContext uiContext;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public UITemplateUIContext getUiContext() {
		return uiContext;
	}

	public void setUiContext(UITemplateUIContext uiContext) {
		this.uiContext = uiContext;
	}
	
}
