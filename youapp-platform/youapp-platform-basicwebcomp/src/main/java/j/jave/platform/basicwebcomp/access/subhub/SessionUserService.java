/**
 * 
 */
package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.basicwebcomp.core.service.SessionUser;

/**
 * the interface to new SessionUser.
 * the concrete system need do only one implementation to provide the session user.
 * @author J
 * @see SessionUser
 */
public interface SessionUserService extends JService ,SessionUserGetListener{

	SessionUser newSessionUser();
	
}
