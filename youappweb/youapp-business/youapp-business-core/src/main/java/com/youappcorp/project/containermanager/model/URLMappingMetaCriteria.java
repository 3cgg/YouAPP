package com.youappcorp.project.containermanager.model;

import j.jave.platform.data.web.model.BaseCriteria;

public class URLMappingMetaCriteria extends BaseCriteria {

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
	
}
