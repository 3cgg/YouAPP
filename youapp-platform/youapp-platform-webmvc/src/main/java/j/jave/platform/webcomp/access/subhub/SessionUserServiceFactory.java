/**
 * 
 */
package j.jave.platform.webcomp.access.subhub;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel.jave.exception.JOperationNotSupportedException;


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
