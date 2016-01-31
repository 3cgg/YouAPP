/**
 * 
 */
package j.jave.framework.components.web.cache.response;

import j.jave.framework.commons.service.JService;

/**
 * indicate the response cached service provided by Ehcache.
 * @author J
 */
public interface ResponseEhcacheMemoryCacheService extends JService{

	boolean isNeedCache(String key);
	
	void add(ResponseCacheModel object);

	void remove(ResponseCacheModel object);
	
	ResponseCacheModel get(String path);
	
}
