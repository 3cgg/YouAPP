/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesRegisterStartNotifyEvent.class)
public interface JServicesRegisterStartNotifyListener extends JYouAPPListener {
	
	public Object trigger(JServicesRegisterStartNotifyEvent event) ;
	
}
