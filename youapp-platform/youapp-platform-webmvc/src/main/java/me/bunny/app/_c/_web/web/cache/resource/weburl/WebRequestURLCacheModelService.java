/**
 * 
 */
package me.bunny.app._c._web.web.cache.resource.weburl;

import java.util.List;

import me.bunny.kernel._c.support.resourceuri.ResourceCacheModelService;


/**
 * system resource interface , to used in request response cached, or any other cases. 
 * @author J
 */
public interface WebRequestURLCacheModelService extends ResourceCacheModelService{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends WebRequestURLCacheModel> getResourceCacheModels();
	
}
