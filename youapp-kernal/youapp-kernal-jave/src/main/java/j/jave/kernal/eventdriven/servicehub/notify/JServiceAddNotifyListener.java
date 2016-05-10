/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceAddNotifyEvent.class)
public interface JServiceAddNotifyListener extends JAPPListener {
	
	public Object trigger(JServiceAddNotifyEvent event) ;
	
}
