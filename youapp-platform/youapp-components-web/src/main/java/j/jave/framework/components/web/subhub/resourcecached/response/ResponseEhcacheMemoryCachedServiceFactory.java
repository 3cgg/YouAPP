/**
 * 
 */
package j.jave.framework.components.web.subhub.resourcecached.response;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="responseEhcacheMemoryCachedServiceFactory")
public class ResponseEhcacheMemoryCachedServiceFactory extends SpringServiceFactorySupport<ResponseEhcacheMemoryCacheService> {
	
	public ResponseEhcacheMemoryCachedServiceFactory() {
		super(ResponseEhcacheMemoryCacheService.class);
	}
	
	@Override
	public ResponseEhcacheMemoryCacheService getService() {
		return getBeanByName("responseEncacheMemoryCacheServiceImpl");
	}
	
	
	@Override
	public String getName() {
		return "Response Memory Cached Service Factory";
	}
}
