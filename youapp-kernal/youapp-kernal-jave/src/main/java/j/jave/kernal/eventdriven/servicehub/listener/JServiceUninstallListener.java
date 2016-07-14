/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceUninstallEvent.class)
public interface JServiceUninstallListener extends JYouAPPListener {
	
	public Object trigger(JServiceUninstallEvent event) ;
	
}
