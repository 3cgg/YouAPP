/**
 * 
 */
package j.jave.framework.components.web.subhub.sessionuser;

import j.jave.framework.servicehub.JService;

/**
 * the interface to new SessionUser.
 * the concrete system need do only one implementation to provide the session user.
 * @author J
 * @see SessionUser
 */
public interface SessionUserService extends JService ,SessionUserGetListener{

	SessionUser newSessionUser();
	
}
