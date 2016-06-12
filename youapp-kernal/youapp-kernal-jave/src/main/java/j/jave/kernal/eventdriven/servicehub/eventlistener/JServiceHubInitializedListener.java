/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceHubInitializedEvent.class)
public interface JServiceHubInitializedListener extends JYouAPPListener {
	
	public Object trigger(JServiceHubInitializedEvent event) ;
	
}
