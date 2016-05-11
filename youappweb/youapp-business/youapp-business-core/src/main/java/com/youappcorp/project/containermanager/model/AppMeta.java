package com.youappcorp.project.containermanager.model;

import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 */
@Entity
@Table(name="AppMeta")
public class AppMeta extends JJpaBaseModel  {
	
	private String appName;
	
	private String appCompName;
	
	private String appVersion;
	
	private String appUnique;
	
	private String appDesc;
	
	private String appActive;
	
	private String appHost;
	
	/**
	 * the friendly url that is the same functionality as {@link #unique()}
	 */
	private String friendlyUrl;

	@Column(name="APP_NAME")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name="APP_UNIQUE")
	public String getAppUnique() {
		return appUnique;
	}

	public void setAppUnique(String appUnique) {
		this.appUnique = appUnique;
	}

	@Column(name="APP_COMP_NAME")
	public String getAppCompName() {
		return appCompName;
	}

	public void setAppCompName(String appCompName) {
		this.appCompName = appCompName;
	}

	@Column(name="APP_VERSION")
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@Column(name="APP_DESC")
	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	@Column(name="APP_ACTIVE")
	public String getAppActive() {
		return appActive;
	}

	public void setAppActive(String appActive) {
		this.appActive = appActive;
	}

	@Column(name="FRIENDLY_URL")
	public String getFriendlyUrl() {
		return friendlyUrl;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		this.friendlyUrl = friendlyUrl;
	}

	@Column(name="APP_HOST")
	public String getAppHost() {
		return appHost;
	}

	public void setAppHost(String appHost) {
		this.appHost = appHost;
	}

	
}
