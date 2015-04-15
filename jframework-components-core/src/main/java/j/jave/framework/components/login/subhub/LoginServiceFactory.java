/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

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
