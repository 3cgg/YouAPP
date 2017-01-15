/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.notify;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServicesRegisterStartNotifyEvent.class)
public interface JServicesRegisterStartNotifyListener extends JYouAPPListener {
	
	public Object trigger(JServicesRegisterStartNotifyEvent event) ;
	
}
