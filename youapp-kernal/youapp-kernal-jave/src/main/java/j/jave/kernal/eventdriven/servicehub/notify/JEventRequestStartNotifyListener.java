/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JEventRequestStartNotifyEvent.class)
public interface JEventRequestStartNotifyListener extends JYouAPPListener {
	
	public Object trigger(JEventRequestStartNotifyEvent event) ;
	
}
