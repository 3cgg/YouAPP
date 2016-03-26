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

}
