/**
 * 
 */
package j.jave.platform.standalone.server.controller;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ControllerServiceFindingEvent.class)
public interface ControllerServiceFindingListener extends JYouAPPListener {
	
	public Object trigger(ControllerServiceFindingEvent event) ;
	
}
