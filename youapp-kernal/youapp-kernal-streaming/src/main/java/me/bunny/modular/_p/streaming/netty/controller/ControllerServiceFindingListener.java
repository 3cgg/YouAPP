/**
 * 
 */
package me.bunny.modular._p.streaming.netty.controller;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=ControllerServiceFindingEvent.class)
public interface ControllerServiceFindingListener extends JYouAPPListener {
	
	public Object trigger(ControllerServiceFindingEvent event) ;
	
}
