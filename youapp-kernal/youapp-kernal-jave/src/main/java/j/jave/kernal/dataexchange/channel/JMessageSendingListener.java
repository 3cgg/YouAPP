/**
 * 
 */
package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JMessageSendingEvent.class)
public interface JMessageSendingListener extends JAPPListener {
	
	public Object trigger(JMessageSendingEvent event) ;
	
}
