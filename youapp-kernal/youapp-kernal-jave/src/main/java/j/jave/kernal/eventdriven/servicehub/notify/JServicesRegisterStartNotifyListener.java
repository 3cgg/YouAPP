/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesRegisterStartNotifyEvent.class)
public interface JServicesRegisterStartNotifyListener extends JAPPListener {
	
	public Object trigger(JServicesRegisterStartNotifyEvent event) ;
	
}
