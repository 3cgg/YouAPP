/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JEventRequestEndNotifyEvent.class)
public interface JEventRequestEndNotifyListener extends JYouAPPListener {
	
	public Object trigger(JEventRequestEndNotifyEvent event) ;
	
}
