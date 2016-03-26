/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.weburl;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheServiceFactory")
public class WebRequestURLCacheServiceFactory extends SpringServiceFactorySupport<WebRequestURLCacheService> {
	
	@Override
	public WebRequestURLCacheService getService() {
		return getBeanByName(WebRequestURLCacheServiceImpl.class.getName());
	}
	
	
}
