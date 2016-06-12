/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceUninstallEvent.class)
public interface JServiceUninstallListener extends JYouAPPListener {
	
	public Object trigger(JServiceUninstallEvent event) ;
	
}
