package com.youappcorp.project.containermanager.vo;

import j.jave.kernal.jave.model.JModel;
import j.jave.platform.data.web.model.JInputModel;

/**
 * @author J
 */
public class AppMetaVO implements JModel,JInputModel  {
	
	private String appName;
	
	private String appCompName;
	
	private String appVersion;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCompName() {
		return appCompName;
	}

	public void setAppCompName(String appCompName) {
		this.appCompName = appCompName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
}
