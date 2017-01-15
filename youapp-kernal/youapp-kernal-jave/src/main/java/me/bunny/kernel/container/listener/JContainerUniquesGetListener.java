/**
 * 
 */
package me.bunny.kernel.container.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerUniquesGetEvent.class)
public interface JContainerUniquesGetListener extends JYouAPPListener{

	public Object trigger(JContainerUniquesGetEvent event);
	
}
