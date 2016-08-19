/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * all implementations can be notified after service hub initialized completely.
 * @author J
 */
@JEventOnListener(name=JServiceHubInitializedEvent.class)
public interface JServiceHubInitializedListener extends JYouAPPListener {
	
	public Object trigger(JServiceHubInitializedEvent event) ;
	
}
