/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceInstallEvent.class)
public interface JServiceInstallListener extends JYouAPPListener {
	
	public Object trigger(JServiceInstallEvent event) ;
	
}
