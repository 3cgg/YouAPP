/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.weburl;

import j.jave.kernal.jave.support.resourceuri.ResourceCacheModelService;

import java.util.List;


/**
 * system resource interface , to used in request response cached, or any other cases. 
 * @author J
 */
public interface WebRequestURLCacheModelService extends ResourceCacheModelService{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WebRequestURLCacheModel> getResourceCacheModels();
	
}
