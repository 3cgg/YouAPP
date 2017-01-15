/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.notify;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceAddNotifyEvent.class)
public interface JServiceAddNotifyListener extends JYouAPPListener {
	
	public Object trigger(JServiceAddNotifyEvent event) ;
	
}
