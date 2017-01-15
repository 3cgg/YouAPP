/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.coderef;

import java.util.List;

import me.bunny.kernel.jave.support.resourceuri.ResourceCacheModelService;


/**
 * system resource interface , to used in request response cached, or any other cases. 
 * @author J
 */
public interface CodeRefCacheModelService extends ResourceCacheModelService{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends CodeRefCacheModel> getResourceCacheModels();
	
}
