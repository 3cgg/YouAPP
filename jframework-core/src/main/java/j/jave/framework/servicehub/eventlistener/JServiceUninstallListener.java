/**
 * 
 */
package j.jave.framework.servicehub.eventlistener;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceUninstallEvent.class)
public interface JServiceUninstallListener extends JAPPListener {
	
	public Object trigger(JServiceUninstallEvent event) ;
	
}
