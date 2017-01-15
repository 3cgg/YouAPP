package me.bunny.app._c._web.web.cache.resource.weburl;

import java.util.List;

import me.bunny.kernel._c.support.resourceuri.ResourceCacheModel;

public interface WebRequestURLCacheModel extends ResourceCacheModel {

	/**
	 * if the url is cached.
	 * @return
	 */
	public boolean isCached();
	
	public List<String> accessUserNames();
	
	public List<String> accessUserIds();
}
