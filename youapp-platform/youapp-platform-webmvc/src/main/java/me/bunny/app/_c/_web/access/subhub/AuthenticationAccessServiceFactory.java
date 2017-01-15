/**
 * 
 */
package me.bunny.app._c._web.access.subhub;

import org.springframework.stereotype.Service;

import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;

/**
 * @author J
 *
 */
@Service(value="AuthenticationAccessServiceFactory")
public class AuthenticationAccessServiceFactory extends SpringServiceFactorySupport<AuthenticationAccessService> {

	public AuthenticationAccessServiceFactory() {
		super(AuthenticationAccessService.class);
	}
	
	@Override
	public AuthenticationAccessService getService() {
		return getBeanByName(AuthenticationAccessServiceImpl.BEAN_NAME);
	}
	
	@Override
	public String getName() {
		return "Authentication Access Service provided by "+this.getClass().getName();
	}
	
}
