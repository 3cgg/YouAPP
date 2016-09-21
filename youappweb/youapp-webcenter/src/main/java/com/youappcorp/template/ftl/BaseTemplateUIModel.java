package com.youappcorp.template.ftl;

import j.jave.kernal.jave.model.JModel;

public class BaseTemplateUIModel implements JModel{
	
	private String filePath;
	
	private String fileName;
	
	private BaseTemplateUIContext uiConext;

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

	public BaseTemplateUIContext getUiConext() {
		return uiConext;
	}

	public void setUiConext(BaseTemplateUIContext uiConext) {
		this.uiConext = uiConext;
	}
	
}
