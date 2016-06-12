/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesRegisterEndNotifyEvent.class)
public interface JServicesRegisterEndNotifyListener extends JYouAPPListener {
	
	public Object trigger(JServicesRegisterEndNotifyEvent event) ;
	
}
