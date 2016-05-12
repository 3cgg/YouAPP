/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JEventRequestEndNotifyEvent.class)
public interface JEventRequestEndNotifyListener extends JAPPListener {
	
	public Object trigger(JEventRequestEndNotifyEvent event) ;
	
}
