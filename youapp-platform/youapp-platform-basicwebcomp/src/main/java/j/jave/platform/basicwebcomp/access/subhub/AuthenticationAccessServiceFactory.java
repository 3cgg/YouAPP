/**
 * 
 */
package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="j.jave.platform.basicwebcomp.login.subhub.AuthenticationAccessServiceFactory")
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
