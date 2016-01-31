/**
 * 
 */
package j.jave.platform.basicwebcomp.login.subhub;

import j.jave.kernal.jave.service.JService;

/**
 * the interface to new SessionUser.
 * the concrete system need do only one implementation to provide the session user.
 * @author J
 * @see SessionUser
 */
public interface SessionUserService extends JService ,SessionUserGetListener{

	SessionUser newSessionUser();
	
}
