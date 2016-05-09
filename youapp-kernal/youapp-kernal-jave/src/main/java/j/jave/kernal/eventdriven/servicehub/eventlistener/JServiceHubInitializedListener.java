/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceHubInitializedEvent.class)
public interface JServiceHubInitializedListener extends JAPPListener {
	
	public Object trigger(JServiceHubInitializedEvent event) ;
	
}
