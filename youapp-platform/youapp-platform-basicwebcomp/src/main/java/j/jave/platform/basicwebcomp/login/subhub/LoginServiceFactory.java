/**
 * 
 */
package j.jave.platform.basicwebcomp.login.subhub;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="loginServiceFactory")
public class LoginServiceFactory extends SpringServiceFactorySupport<LoginAccessService> {

	public LoginServiceFactory() {
		super(LoginAccessService.class);
	}
	
	@Override
	public LoginAccessService getService() {
		return getBeanByName("loginAccessService");
	}
	
	@Override
	public String getName() {
		return "Login Service provided by "+this.getClass().getName();
	}
	
}
