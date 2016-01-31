/**
 * 
 */
package j.jave.framework.components.web.cache.resource;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.framework.components.resource.subhub.ResourceCachedServiceFactory")
public class ResourceCacheServiceFactory extends SpringServiceFactorySupport<ResourceCacheService> {

	public ResourceCacheServiceFactory() {
		super(ResourceCacheService.class);
	}
	
	/**
	 * @param registClass
	 */
	public ResourceCacheServiceFactory(Class<ResourceCacheService> registClass) {
		super(registClass);
	}
	
	@Override
	public ResourceCacheService getService() {
		return getBeanByName("j.jave.framework.components.resource.subhub.ResourceCachedServiceImpl");
	}

	
	
	
}
