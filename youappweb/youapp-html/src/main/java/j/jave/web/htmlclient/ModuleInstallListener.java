/**
 * 
 */
package j.jave.web.htmlclient;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ModuleInstallEvent.class)
public interface ModuleInstallListener extends JYouAPPListener {
	
	public Object trigger(ModuleInstallEvent event) ;
	
}
