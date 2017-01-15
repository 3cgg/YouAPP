/**
 * 
 */
package me.bunny.app._c._web.web.cache.response;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="ResponseCacheServiceFactory")
public class ResponseCacheServiceFactory extends ResponseEhcacheCacheServiceFactory {
	
	@Override
	public ResponseEhcacheCacheService getService() {
		return getBeanByName(ResponseCacheServiceImpl.BEAN_NAME);
	}
	
	@Override
	public String getName() {
		return "Associated to any resource url cache.";
	}
}
