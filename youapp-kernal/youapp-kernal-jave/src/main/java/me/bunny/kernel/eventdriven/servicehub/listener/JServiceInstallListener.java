/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceInstallEvent.class)
public interface JServiceInstallListener extends JYouAPPListener {
	
	public Object trigger(JServiceInstallEvent event) ;
	
}
