/**
 * 
 */
package j.jave.platform.basicwebcomp.login.subhub;

import j.jave.platform.basicwebcomp.core.model.SessionUserInfo;

import org.springframework.stereotype.Service;

/**
 * dynamic to provide session user implementation. the class can response from the SessionUserGetEvent event.
 * @author J
 * @see SessionUserGetEvent
 * @see SessionUserGetListener
 */
@Service(value="j.jave.framework.components.login.subhub.SessionUserServiceImpl")
public class SessionUserServiceImpl implements SessionUserService{

	@Override
	public SessionUser newSessionUser() {
		return new SessionUserInfo();
	}

	@Override
	public SessionUser trigger(SessionUserGetEvent event) {
		return newSessionUser();
	}

}
