/**
 * 
 */
package j.jave.framework.components.resource.cache;

import j.jave.framework.components.resource.service.ResourceExtendService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * the implementation implements ResourceCachedService expose those resources ,
 * that can be controlled to on or off the page cached.
 * @author J
 * @see ResourceCachedService
 * @see ResponseEhcacheMemoryCacheService
 * @see MemoryCachedService
 */
@Service(value="j.jave.framework.components.resource.subhub.ResourceCachedServiceImpl")
public class ResourceCacheServiceImpl  implements ResourceCacheService{
	
	@Autowired
	private ResourceExtendService resourceExtendService;

	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceCacheModel> getResourceCacheModel() {
		List<?> resources= resourceExtendService.getAllResourceExtends(null);
		return (List<ResourceCacheModel>) resources;
	}

	@Override
	public ResourceCacheService trigger(ResourceCacheServiceGetEvent event) {
		return this;
	}

}
