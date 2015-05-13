/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;
import j.jave.framework.components.web.subhub.sessionuser.SessionUserService;

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
