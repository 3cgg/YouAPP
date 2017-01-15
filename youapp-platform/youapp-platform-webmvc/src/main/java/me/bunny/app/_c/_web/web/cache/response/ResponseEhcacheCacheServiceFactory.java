/**
 * 
 */
package me.bunny.app._c._web.web.cache.response;

import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel._c.exception.JOperationNotSupportedException;

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
