/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.notify;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesRegisterEndNotifyEvent.class)
public interface JServicesRegisterEndNotifyListener extends JYouAPPListener {
	
	public Object trigger(JServicesRegisterEndNotifyEvent event) ;
	
}
