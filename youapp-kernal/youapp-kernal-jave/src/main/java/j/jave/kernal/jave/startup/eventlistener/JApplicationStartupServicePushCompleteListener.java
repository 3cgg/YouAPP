/**
 * 
 */
package j.jave.kernal.jave.startup.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JApplicationStartupServicePushCompleteEvent.class)
public interface JApplicationStartupServicePushCompleteListener extends JAPPListener {
	
	public Object trigger(JApplicationStartupServicePushCompleteEvent event) ;
	
}
