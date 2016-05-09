/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesOnListenerEvent.class)
public interface JServicesOnListenerListener extends JAPPListener {
	
	public Object trigger(JServicesOnListenerEvent event) ;
	
}
