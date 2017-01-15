/**
 * 
 */
package me.bunny.kernel.container.listener;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JContainerRegisterEvent.class)
public interface JContainerRegisterListener extends JYouAPPListener{

	public Object trigger(JContainerRegisterEvent event);
	
}
