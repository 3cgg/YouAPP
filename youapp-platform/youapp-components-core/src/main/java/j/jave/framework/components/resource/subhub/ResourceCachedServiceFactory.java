/**
 * 
 */
package j.jave.framework.components.resource.subhub;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCachedService;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.framework.components.resource.subhub.ResourceCachedServiceFactory")
public class ResourceCachedServiceFactory extends SpringServiceFactorySupport<ResourceCachedService> {

	public ResourceCachedServiceFactory() {
		super(ResourceCachedService.class);
	}
	
	/**
	 * @param registClass
	 */
	public ResourceCachedServiceFactory(Class<ResourceCachedService> registClass) {
		super(registClass);
	}
	
	@Override
	public ResourceCachedService getService() {
		return getBeanByName("j.jave.framework.components.resource.subhub.ResourceCachedServiceImpl");
	}

	
	
	
}
