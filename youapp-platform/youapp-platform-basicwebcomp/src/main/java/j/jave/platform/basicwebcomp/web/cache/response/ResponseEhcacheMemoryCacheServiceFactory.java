/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.response;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

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
