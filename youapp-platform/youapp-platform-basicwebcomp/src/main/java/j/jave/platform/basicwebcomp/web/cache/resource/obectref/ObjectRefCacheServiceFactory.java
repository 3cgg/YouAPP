/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.obectref;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.platform.basicwebcomp.web.cache.resource.obectref.ObjectRefCacheServiceFactory")
public class ObjectRefCacheServiceFactory extends SpringServiceFactorySupport<ObjectRefCacheService> {
	
	@Override
	public ObjectRefCacheService getService() {
		return getBeanByName(ObjectRefCacheServiceImpl.class.getName());
	}
	
	
}
