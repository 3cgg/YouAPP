/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceInstallEvent.class)
public interface JServiceInstallListener extends JAPPListener {
	
	public Object trigger(JServiceInstallEvent event) ;
	
}
