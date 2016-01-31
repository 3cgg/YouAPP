/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerEnableEvent.class)
public interface JServiceListenerEnableListener extends JAPPListener {
	
	public Object trigger(JServiceListenerEnableEvent event) ;
	
}
