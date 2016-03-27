/**
 * 
 */
package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;


/**
 * Session User Service Factory .
 * @author J
 */
@Service(value="j.jave.framework.components.login.subhub.SessionUserFactory")
public class SessionUserServiceFactory extends SpringServiceFactorySupport<SessionUserService>{
	
	@Override
	public SessionUserService getService() {
		return getBeanByName("j.jave.framework.components.login.subhub.SessionUserServiceImpl");
	}

}
