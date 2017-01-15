/**
 * 
 */
package me.bunny.app._c.sps.core.servicehub;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=SpringApplicationContextInitializedEvent.class)
public interface SpringApplicationContextInitializedListener extends JYouAPPListener {
	
	public Object trigger(SpringApplicationContextInitializedEvent event) ;
	
}
