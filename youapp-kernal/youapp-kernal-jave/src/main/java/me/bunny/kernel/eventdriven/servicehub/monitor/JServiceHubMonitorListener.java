/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.monitor;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceHubMonitorEvent.class)
public interface JServiceHubMonitorListener extends JYouAPPListener {
	
	public JServiceHubMeta trigger(JServiceHubMonitorEvent event) ;
	
}
