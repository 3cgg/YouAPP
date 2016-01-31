/**
 * 
 */
package j.jave.framework.components.resource.cache;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.framework.components.web.cache.response.ResponseEhcacheMemoryCacheServiceFactory")
public class ResponseCacheServiceFactory extends ResponseEhcacheMemoryCacheServiceFactory {
	
	@Override
	public ResponseEhcacheMemoryCacheService getService() {
		return getBeanByName("j.jave.framework.components.resource.cache.ResponseCacheServiceImpl");
	}
	
	@Override
	public String getName() {
		return "Associated to any resource table";
	}
}
