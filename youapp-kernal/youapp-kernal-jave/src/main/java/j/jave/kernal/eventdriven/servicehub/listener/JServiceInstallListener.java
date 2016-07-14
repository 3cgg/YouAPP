/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceInstallEvent.class)
public interface JServiceInstallListener extends JYouAPPListener {
	
	public Object trigger(JServiceInstallEvent event) ;
	
}
