/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.weburl;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

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
