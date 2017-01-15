/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceUninstallEvent.class)
public interface JServiceUninstallListener extends JYouAPPListener {
	
	public Object trigger(JServiceUninstallEvent event) ;
	
}
