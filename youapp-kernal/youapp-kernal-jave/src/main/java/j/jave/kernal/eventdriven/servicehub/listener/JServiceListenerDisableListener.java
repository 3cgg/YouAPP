/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerDisableEvent.class)
public interface JServiceListenerDisableListener extends JYouAPPListener {
	
	public Object trigger(JServiceListenerDisableEvent event) ;
	
}
