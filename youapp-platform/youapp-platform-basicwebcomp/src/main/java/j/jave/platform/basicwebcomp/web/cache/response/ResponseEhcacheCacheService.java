/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.response;

import j.jave.kernal.jave.service.JService;

/**
 * indicate the response cached service provided by Ehcache.
 * @author J
 */
public interface ResponseEhcacheCacheService extends JService{

	boolean isNeedCache(String key);
	
	void add(ResponseCacheModel object);

	void remove(ResponseCacheModel object);
	
	ResponseCacheModel get(String path);
	
}
