/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * all implementations can be notified after service hub initialized completely.
 * @author J
 */
@JEventOnListener(name=JServiceHubInitializedEvent.class)
public interface JServiceHubInitializedListener extends JYouAPPListener {
	
	public Object trigger(JServiceHubInitializedEvent event) ;
	
}
