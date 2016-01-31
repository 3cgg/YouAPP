/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceListenerDisableEvent.class)
public interface JServiceListenerDisableListener extends JAPPListener {
	
	public Object trigger(JServiceListenerDisableEvent event) ;
	
}
