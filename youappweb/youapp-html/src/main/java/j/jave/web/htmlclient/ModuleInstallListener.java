/**
 * 
 */
package j.jave.web.htmlclient;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=ModuleInstallEvent.class)
public interface ModuleInstallListener extends JYouAPPListener {
	
	public Object trigger(ModuleInstallEvent event) ;
	
}
