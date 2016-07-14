/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerEnableEvent.class)
public interface JServiceListenerEnableListener extends JYouAPPListener {
	
	public Object trigger(JServiceListenerEnableEvent event) ;
	
}
