/**
 * 
 */
package me.bunny.app._c._web.web.cache.resource.weburl;

import org.springframework.stereotype.Service;

import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;

/**
 * @author J
 */
@Service(value="WebRequestURLCacheServiceFactory")
public class WebRequestURLCacheServiceFactory extends SpringServiceFactorySupport<WebRequestURLCacheService> {
	
	@Override
	public WebRequestURLCacheService getService() {
		return getBeanByName(WebRequestURLCacheServiceImpl.BEAN_NAME);
	}
	
	
}
