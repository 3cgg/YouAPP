/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.obectref;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="ObjectRefCacheServiceFactory")
public class ObjectRefCacheServiceFactory extends SpringServiceFactorySupport<ObjectRefCacheService> {
	
	@Override
	public ObjectRefCacheService getService() {
		return getBeanByName(ObjectRefCacheServiceImpl.class.getName());
	}
	
	
}
