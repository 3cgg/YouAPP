/**
 * 
 */
package j.jave.kernal.streaming.netty.controller;

import j.jave.kernal.eventdriven.servicehub.JEventOnListener;
import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=ControllerServiceFindingEvent.class)
public interface ControllerServiceFindingListener extends JYouAPPListener {
	
	public Object trigger(ControllerServiceFindingEvent event) ;
	
}
