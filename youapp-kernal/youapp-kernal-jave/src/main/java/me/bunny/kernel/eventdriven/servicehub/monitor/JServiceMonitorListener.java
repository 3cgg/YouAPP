/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.monitor;

import java.util.List;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JServiceMonitorEvent.class)
public interface JServiceMonitorListener extends JYouAPPListener {
	
	public List<JServiceRuntimeMeta> trigger(JServiceMonitorEvent event) ;
	
}
