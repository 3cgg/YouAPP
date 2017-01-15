/**
 * 
 */
package me.bunny.kernel.container.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerGetEvent.class)
public interface JContainerGetListener extends JYouAPPListener{

	public Object trigger(JContainerGetEvent event);
	
}
