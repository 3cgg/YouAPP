/**
 * 
 */
package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JMessageSendingEvent.class)
public interface JMessageSendingListener extends JYouAPPListener {
	
	public Object trigger(JMessageSendingEvent event) ;
	
}
