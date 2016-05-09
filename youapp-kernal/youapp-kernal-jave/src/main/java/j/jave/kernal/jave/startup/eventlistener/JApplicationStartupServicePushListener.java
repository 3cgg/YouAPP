/**
 * 
 */
package j.jave.kernal.jave.startup.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JApplicationStartupServicePushEvent.class)
public interface JApplicationStartupServicePushListener extends JAPPListener {
	
	public Object trigger(JApplicationStartupServicePushEvent event) ;
	
}
