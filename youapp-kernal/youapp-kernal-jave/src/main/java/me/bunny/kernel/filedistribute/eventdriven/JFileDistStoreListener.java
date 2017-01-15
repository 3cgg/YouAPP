/**
 * 
 */
package me.bunny.kernel.filedistribute.eventdriven;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=JFileDistStoreEvent.class)
public interface JFileDistStoreListener extends JYouAPPListener{

	public Object trigger(JFileDistStoreEvent event);
	
}
