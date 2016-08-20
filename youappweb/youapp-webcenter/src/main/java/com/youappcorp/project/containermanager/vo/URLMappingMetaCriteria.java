package com.youappcorp.project.containermanager.vo;

import j.jave.platform.data.web.model.BaseCriteria;

public class URLMappingMetaCriteria extends BaseCriteria {

	private String appId;
	
	private String urlDesc;
	
	private String url;

	public String getUrlDesc() {
		return urlDesc;
	}

	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
