/**
 * 
 */
package me.bunny.app._c._web.access.subhub;

import me.bunny.app._c._web.core.service.SessionUser;
import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * any sub-class implementation can provide the SessionUserService .
 * <strong>Note that the system must implement the interface via registering the service in the service hub to enable user session.</strong>
 * @author J
 * @see SessionUserService
 * @see JServiceHubDelegate
 */
@JEventOnListener(name=SessionUserGetEvent.class)
public interface SessionUserGetListener extends JYouAPPListener {
	SessionUser trigger(SessionUserGetEvent event); 
}
