/**
 * 
 */
package me.bunny.app._c._web.web.cache.resource.weburl;

import me.bunny.kernel._c.support.resourceuri.ResourceCacheService;
import me.bunny.kernel._c.support.resourceuri.ResourceCacheServiceGetListener;



/**
 * system resource interface , to used in request response cached. 
 * @author J
 */
public interface WebRequestURLCacheService extends ResourceCacheService<WebRequestURLCacheModel>, ResourceCacheServiceGetListener{
	
	boolean isNeedCache(String path);
}
