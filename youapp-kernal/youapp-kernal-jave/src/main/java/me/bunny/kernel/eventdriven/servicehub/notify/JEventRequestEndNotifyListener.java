/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.notify;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JEventRequestEndNotifyEvent.class)
public interface JEventRequestEndNotifyListener extends JYouAPPListener {
	
	public Object trigger(JEventRequestEndNotifyEvent event) ;
	
}
