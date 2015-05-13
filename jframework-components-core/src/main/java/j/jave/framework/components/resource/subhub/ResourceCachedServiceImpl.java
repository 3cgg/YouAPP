/**
 * 
 */
package j.jave.framework.components.resource.subhub;

import j.jave.framework.components.resource.service.ResourceExtendService;
import j.jave.framework.components.web.subhub.resourcecached.MemoryCachedService;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCached;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCachedService;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCachedServiceGetEvent;
import j.jave.framework.components.web.subhub.resourcecached.response.ResponseEhcacheMemoryCacheService;

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
public class ResourceCachedServiceImpl  implements ResourceCachedService{
	
	@Autowired
	private ResourceExtendService resourceExtendService;

	@Override
	public List<ResourceCached> getResourceCached() {
		List<?> resources= resourceExtendService.getAllResourceExtends(null);
		return (List<ResourceCached>) resources;
	}

	@Override
	public ResourceCachedService trigger(ResourceCachedServiceGetEvent event) {
		return this;
	}

}
