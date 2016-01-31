/**
 * 
 */
package j.jave.framework.components.web.cache.response;

import j.jave.framework.commons.exception.JOperationNotSupportedException;
import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

/**
 * @author J
 */
public abstract class ResponseEhcacheMemoryCacheServiceFactory extends SpringServiceFactorySupport<ResponseEhcacheMemoryCacheService> {
	
	public ResponseEhcacheMemoryCacheServiceFactory() {
		super(ResponseEhcacheMemoryCacheService.class);
	}
	
	@Override
	public ResponseEhcacheMemoryCacheService getService() {
		throw new JOperationNotSupportedException("PLEASE provide any supported service,extends "+ResponseEhcacheMemoryCacheServiceImpl.class.getName());
	}
	
	@Override
	public String getName() {
		return "Response Memory Cached Service Factory";
	}
}
