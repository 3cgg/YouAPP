/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub.eventlistener;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceUninstallEvent.class)
public interface JServiceUninstallListener extends JAPPListener {
	
	public Object trigger(JServiceUninstallEvent event) ;
	
}
