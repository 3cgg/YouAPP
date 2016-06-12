/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceAddNotifyEvent.class)
public interface JServiceAddNotifyListener extends JYouAPPListener {
	
	public Object trigger(JServiceAddNotifyEvent event) ;
	
}
