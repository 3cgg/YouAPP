/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceMonitorEvent.class)
public interface JServiceMonitorListener extends JAPPListener {
	
	public JServiceHubMeta trigger(JServiceMonitorEvent event) ;
	
}
