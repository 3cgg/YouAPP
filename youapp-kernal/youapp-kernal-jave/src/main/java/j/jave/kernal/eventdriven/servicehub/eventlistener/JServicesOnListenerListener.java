/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesOnListenerEvent.class)
public interface JServicesOnListenerListener extends JYouAPPListener {
	
	public Object trigger(JServicesOnListenerEvent event) ;
	
}
