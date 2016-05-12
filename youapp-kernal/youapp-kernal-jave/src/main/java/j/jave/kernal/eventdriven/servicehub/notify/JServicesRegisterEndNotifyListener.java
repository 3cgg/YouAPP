/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesRegisterEndNotifyEvent.class)
public interface JServicesRegisterEndNotifyListener extends JAPPListener {
	
	public Object trigger(JServicesRegisterEndNotifyEvent event) ;
	
}
