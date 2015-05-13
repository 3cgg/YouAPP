/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.components.core.model.SessionUserInfo;
import j.jave.framework.components.web.subhub.sessionuser.SessionUser;
import j.jave.framework.components.web.subhub.sessionuser.SessionUserGetEvent;
import j.jave.framework.components.web.subhub.sessionuser.SessionUserGetListener;
import j.jave.framework.components.web.subhub.sessionuser.SessionUserService;

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
