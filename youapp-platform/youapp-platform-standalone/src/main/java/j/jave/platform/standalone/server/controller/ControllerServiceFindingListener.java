/**
 * 
 */
package j.jave.platform.standalone.server.controller;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=ControllerServiceFindingEvent.class)
public interface ControllerServiceFindingListener extends JYouAPPListener {
	
	public Object trigger(ControllerServiceFindingEvent event) ;
	
}
