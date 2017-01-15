/**
 * 
 */
package me.bunny.kernel._c.sync;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JSyncMonitorWakeupEvent.class)
public interface JSyncMonitorWakeupListener extends JYouAPPListener {
	
	public Object trigger(JSyncMonitorWakeupEvent event) ;
	
}
