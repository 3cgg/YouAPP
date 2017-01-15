/**
 * 
 */
package me.bunny.kernel.jave.support.resourceuri;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=ResourceCacheRefreshEvent.class)
public interface ResourceCacheRefreshListener extends JYouAPPListener {

	public Object trigger(ResourceCacheRefreshEvent event);
	
}
