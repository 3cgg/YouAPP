/**
 * 
 */
package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ObjectTransModelSendingEvent.class)
public interface ObjectTransModelSendingListener extends JAPPListener {
	
	public Object trigger(ObjectTransModelSendingEvent event) ;
	
}
