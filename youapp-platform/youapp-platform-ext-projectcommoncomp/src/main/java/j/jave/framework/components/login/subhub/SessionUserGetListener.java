/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;

/**
 * any sub-class implementation can provide the SessionUserService .
 * <strong>Note that the system must implement the interface via registering the service in the service hub to enable user session.</strong>
 * @author J
 * @see SessionUserService
 * @see JServiceHubDelegate
 */
@JEventOnListener(name=SessionUserGetEvent.class)
public interface SessionUserGetListener extends JAPPListener {
	SessionUser trigger(SessionUserGetEvent event); 
}
