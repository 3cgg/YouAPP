/**
 * 
 */
package me.bunny.app._c._web.access.subhub;

import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel._c.exception.JOperationNotSupportedException;


/**
 * Session User Service Factory .
 * @author J
 */
public abstract class SessionUserServiceFactory extends SpringServiceFactorySupport<SessionUserService>{
	
	public SessionUserServiceFactory() {
		throw new JOperationNotSupportedException("the empty constructor is not supported.");
	}
	
	public SessionUserServiceFactory(Class<SessionUserService> registClass) {
		super(registClass);
	}
	
	@Override
	public final SessionUserService getService() {
		return doGetService();
	}

	protected abstract SessionUserService doGetService();
	
}
