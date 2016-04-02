package com.youappcorp.project.websupport.model;

import j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheModel;

import java.util.List;

public class WebRequestURLCacheModelImpl implements WebRequestURLCacheModel {

	private String url;
	
	private boolean cached;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setCached(boolean cached) {
		this.cached = cached;
	}

	@Override
	public String getUri() {
		return url;
	}

	@Override
	public boolean isCached() {
		return cached;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public List<String> accessUserNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> accessUserIds() {
		// TODO Auto-generated method stub
		return null;
	}

}
