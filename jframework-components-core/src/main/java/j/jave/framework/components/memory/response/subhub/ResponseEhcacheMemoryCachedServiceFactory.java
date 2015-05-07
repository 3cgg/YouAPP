/**
 * 
 */
package j.jave.framework.components.memory.response.subhub;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="responseEhcacheMemoryCachedServiceFactory")
public class ResponseEhcacheMemoryCachedServiceFactory extends SpringServiceFactorySupport<ResponseEncacheMemoryCacheService> {
	
	public ResponseEhcacheMemoryCachedServiceFactory() {
		super(ResponseEncacheMemoryCacheService.class);
	}
	
	@Override
	public ResponseEncacheMemoryCacheService getService() {
		return getBeanByName("responseEncacheMemoryCacheServiceImpl");
	}
	
	
	@Override
	public String getName() {
		return "Response Memory Cached Service Factory";
	}
}
