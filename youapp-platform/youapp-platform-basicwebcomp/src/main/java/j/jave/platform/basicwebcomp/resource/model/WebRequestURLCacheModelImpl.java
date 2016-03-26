package j.jave.platform.basicwebcomp.resource.model;

import j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheModel;

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
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCached() {
		// TODO Auto-generated method stub
		return false;
	}

}
