/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.coderef;

import me.bunny.kernel._c.support.resourceuri.ResourceCacheService;



/**
 * cache all code-names.
 * @author J
 */
public interface CodeRefCacheService<T> extends ResourceCacheService<T>{
	
	public String getName(String type,String code);
	
}
