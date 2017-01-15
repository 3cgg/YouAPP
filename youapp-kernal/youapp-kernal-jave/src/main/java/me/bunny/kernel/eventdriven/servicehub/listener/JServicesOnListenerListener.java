/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesOnListenerEvent.class)
public interface JServicesOnListenerListener extends JYouAPPListener {
	
	public Object trigger(JServicesOnListenerEvent event) ;
	
}
