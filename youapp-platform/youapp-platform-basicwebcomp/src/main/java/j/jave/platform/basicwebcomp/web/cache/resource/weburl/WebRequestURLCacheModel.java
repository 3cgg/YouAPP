package j.jave.platform.basicwebcomp.web.cache.resource.weburl;

import j.jave.kernal.jave.support.resourceuri.ResourceCacheModel;

public interface WebRequestURLCacheModel extends ResourceCacheModel {

	/**
	 * if the url is cached.
	 * @return
	 */
	public boolean isCached();
	
}
