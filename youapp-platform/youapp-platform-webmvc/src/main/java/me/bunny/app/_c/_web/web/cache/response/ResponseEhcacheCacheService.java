/**
 * 
 */
package me.bunny.app._c._web.web.cache.response;

import me.bunny.kernel._c.service.JService;

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
