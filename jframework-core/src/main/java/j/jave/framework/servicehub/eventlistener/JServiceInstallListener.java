/**
 * 
 */
package j.jave.framework.servicehub.eventlistener;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceInstallEvent.class)
public interface JServiceInstallListener extends JAPPListener {
	
	public Object trigger(JServiceInstallEvent event) ;
	
}
