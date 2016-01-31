/**
 * 
 */
package j.jave.platform.basicwebcomp.login.subhub;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;


/**
 * Session User Service Factory .
 * @author J
 */
@Service(value="j.jave.framework.components.login.subhub.SessionUserFactory")
public class SessionUserServiceFactory extends SpringServiceFactorySupport<SessionUserService>{

	public SessionUserServiceFactory() {
		super(SessionUserService.class);
	}
	
	/**
	 * @param registClass
	 */
	public SessionUserServiceFactory(Class<SessionUserService> registClass) {
		super(registClass);
	}
	
	@Override
	public SessionUserService getService() {
		return getBeanByName("j.jave.framework.components.login.subhub.SessionUserServiceImpl");
	}

	

}
