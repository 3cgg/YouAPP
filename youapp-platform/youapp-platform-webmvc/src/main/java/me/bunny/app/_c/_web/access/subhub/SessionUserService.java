/**
 * 
 */
package me.bunny.app._c._web.access.subhub;

import me.bunny.app._c._web.core.service.SessionUser;
import me.bunny.kernel._c.service.JService;

/**
 * the interface to new SessionUser.
 * the concrete system need do only one implementation to provide the session user.
 * @author J
 * @see SessionUser
 */
public interface SessionUserService extends JService ,SessionUserGetListener{

	SessionUser newSessionUser();
	
}
