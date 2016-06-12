/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceHubMonitorEvent.class)
public interface JServiceHubMonitorListener extends JYouAPPListener {
	
	public JServiceHubMeta trigger(JServiceHubMonitorEvent event) ;
	
}
