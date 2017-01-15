/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerDisableEvent.class)
public interface JServiceListenerDisableListener extends JYouAPPListener {
	
	public Object trigger(JServiceListenerDisableEvent event) ;
	
}
