/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.weburl;

import me.bunny.kernel.jave.support.resourceuri.ResourceCacheService;
import me.bunny.kernel.jave.support.resourceuri.ResourceCacheServiceGetListener;



/**
 * system resource interface , to used in request response cached. 
 * @author J
 */
public interface WebRequestURLCacheService extends ResourceCacheService<WebRequestURLCacheModel>, ResourceCacheServiceGetListener{
	
	boolean isNeedCache(String path);
}
