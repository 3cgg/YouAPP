/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.response;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.platform.basicwebcomp.web.cache.response.ResponseCacheServiceFactory")
public class ResponseCacheServiceFactory extends ResponseEhcacheCacheServiceFactory {
	
	@Override
	public ResponseEhcacheCacheService getService() {
		return getBeanByName(ResponseCacheServiceImpl.class.getName());
	}
	
	@Override
	public String getName() {
		return "Associated to any resource url cache.";
	}
}
