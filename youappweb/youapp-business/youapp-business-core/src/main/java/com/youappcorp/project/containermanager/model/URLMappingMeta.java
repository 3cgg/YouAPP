package com.youappcorp.project.containermanager.model;

import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 */
@Entity
@Table(name="UrlMappingMeta")
public class URLMappingMeta extends JJpaBaseModel  {
	
	private String appId;
	
	private String url;
	
	private String urlType;
	
	private String urlDesc;
	
	private String urlName;
	
	private String urlActive;

	@Column(name="APP_ID")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name="URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="URL_TYPE")
	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	@Column(name="URL_DESC")
	public String getUrlDesc() {
		return urlDesc;
	}

	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}

	@Column(name="URL_NAME")
	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	@Column(name="URL_ACTIVE")
	public String getUrlActive() {
		return urlActive;
	}

	public void setUrlActive(String urlActive) {
		this.urlActive = urlActive;
	}
	
}
