/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.response;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

/**
 * @author J
 */
public abstract class ResponseEhcacheCacheServiceFactory extends SpringServiceFactorySupport<ResponseEhcacheCacheService> {
	
	public ResponseEhcacheCacheServiceFactory() {
		super(ResponseEhcacheCacheService.class);
	}
	
	@Override
	public ResponseEhcacheCacheService getService() {
		throw new JOperationNotSupportedException("PLEASE provide any supported service,extends "+AbstractResponseEhcacheCacheService.class.getName());
	}
	
	@Override
	public String getName() {
		return "Response Memory Cached Service Factory";
	}
}
