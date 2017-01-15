/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.notify;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JEventRequestStartNotifyEvent.class)
public interface JEventRequestStartNotifyListener extends JYouAPPListener {
	
	public Object trigger(JEventRequestStartNotifyEvent event) ;
	
}
