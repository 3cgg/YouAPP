/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceHubMonitorEvent.class)
public interface JServiceHubMonitorListener extends JAPPListener {
	
	public JServiceHubMeta trigger(JServiceHubMonitorEvent event) ;
	
}
