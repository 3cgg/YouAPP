package com.youappcorp.project.containermanager.vo;

import j.jave.platform.data.web.model.JInputModel;

public class DeployModule implements JInputModel {

	private String jarUri;
	
	private boolean override;

	public String getJarUri() {
		return jarUri;
	}

	public void setJarUri(String jarUri) {
		this.jarUri = jarUri;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}
	
	
	
}
