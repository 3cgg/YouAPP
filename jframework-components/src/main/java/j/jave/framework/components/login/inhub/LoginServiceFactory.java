/**
 * 
 */
package j.jave.framework.components.login.inhub;

import j.jave.framework.components.core.hub.ServiceFactory;
import j.jave.framework.components.core.hub.ServiceHub;
import j.jave.framework.components.login.service.LoginAccessService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="loginServiceFactory")
public class LoginServiceFactory implements ServiceFactory<LoginAccessService> , ApplicationContextAware ,InitializingBean  {

	private ApplicationContext applicationContext=null;
	
	@Override
	public LoginAccessService getService() {
		return applicationContext.getBean("loginAccessService", LoginAccessService.class); 
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		ServiceHub.get().register(ServiceHub.StandardServiceInterfaces.LOGIN_ACCESS_SERVICE, this);
	}
}
