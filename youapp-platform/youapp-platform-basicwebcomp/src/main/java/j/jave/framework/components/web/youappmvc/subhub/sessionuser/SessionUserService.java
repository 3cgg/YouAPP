/**
 * 
 */
package j.jave.framework.components.web.youappmvc.subhub.sessionuser;

import j.jave.framework.commons.service.JService;

/**
 * the interface to new SessionUser.
 * the concrete system need do only one implementation to provide the session user.
 * @author J
 * @see SessionUser
 */
public interface SessionUserService extends JService ,SessionUserGetListener{

	SessionUser newSessionUser();
	
}
