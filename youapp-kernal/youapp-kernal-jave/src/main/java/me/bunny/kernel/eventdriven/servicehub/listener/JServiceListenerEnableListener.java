/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerEnableEvent.class)
public interface JServiceListenerEnableListener extends JYouAPPListener {
	
	public Object trigger(JServiceListenerEnableEvent event) ;
	
}
