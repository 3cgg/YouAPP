package j.jave.platform.webcomp.web.cache.resource.weburl;

import java.util.List;

import j.jave.kernal.jave.support.resourceuri.ResourceCacheModel;

public interface WebRequestURLCacheModel extends ResourceCacheModel {

	/**
	 * if the url is cached.
	 * @return
	 */
	public boolean isCached();
	
	public List<String> accessUserNames();
	
	public List<String> accessUserIds();
}
