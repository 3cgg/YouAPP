/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.obectref;

import org.springframework.stereotype.Service;

import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;

/**
 * @author J
 */
@Service(value="ObjectRefCacheServiceFactory")
public class ObjectRefCacheServiceFactory extends SpringServiceFactorySupport<ObjectRefCacheService> {
	
	@Override
	public ObjectRefCacheService getService() {
		return getBeanByName(ObjectRefCacheServiceImpl.BEAN_NAME);
	}
	
	
}
