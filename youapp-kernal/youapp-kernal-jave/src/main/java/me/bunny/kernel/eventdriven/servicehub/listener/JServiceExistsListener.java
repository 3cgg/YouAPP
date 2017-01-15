/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceExistsEvent.class)
public interface JServiceExistsListener extends JYouAPPListener {
	
	public Object trigger(JServiceExistsEvent event) ;
	
}
