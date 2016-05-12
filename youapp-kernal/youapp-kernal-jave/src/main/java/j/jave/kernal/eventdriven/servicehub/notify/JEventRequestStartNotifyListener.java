/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JEventRequestStartNotifyEvent.class)
public interface JEventRequestStartNotifyListener extends JAPPListener {
	
	public Object trigger(JEventRequestStartNotifyEvent event) ;
	
}
