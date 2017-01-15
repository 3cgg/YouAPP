/**
 * 
 */
package me.bunny.modular._p.streaming.netty.client;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=ControllerCallPromiseEvent.class)
public interface ControllerCallPromiseListener extends JYouAPPListener {
	
	public Object trigger(ControllerCallPromiseEvent event) ;
	
}
