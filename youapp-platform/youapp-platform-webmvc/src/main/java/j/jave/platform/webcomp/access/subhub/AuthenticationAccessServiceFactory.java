/**
 * 
 */
package j.jave.platform.webcomp.access.subhub;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

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
		return getBeanByName(AuthenticationAccessServiceImpl.class.getName());
	}
	
	@Override
	public String getName() {
		return "Authentication Access Service provided by "+this.getClass().getName();
	}
	
}
