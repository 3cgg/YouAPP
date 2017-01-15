/**
 * 
 */
package me.bunny.kernel.dataexchange.channel;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JMessageSendingEvent.class)
public interface JMessageSendingListener extends JYouAPPListener {
	
	public Object trigger(JMessageSendingEvent event) ;
	
}
