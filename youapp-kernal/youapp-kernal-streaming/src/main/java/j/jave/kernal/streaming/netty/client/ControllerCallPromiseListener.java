/**
 * 
 */
package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.eventdriven.servicehub.JEventOnListener;
import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=ControllerCallPromiseEvent.class)
public interface ControllerCallPromiseListener extends JYouAPPListener {
	
	public Object trigger(ControllerCallPromiseEvent event) ;
	
}
