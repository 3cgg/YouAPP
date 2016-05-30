/**
 * 
 */
package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=MessageSendingEvent.class)
public interface MessageSendingListener extends JAPPListener {
	
	public Object trigger(MessageSendingEvent event) ;
	
}
