/**
 * 
 */
package me.bunny.app._c._web.web.cache.response;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=ResponseCacheRefreshEvent.class)
public interface ResponseCacheRefreshListener extends JYouAPPListener {

	public Object trigger(ResponseCacheRefreshEvent event);
	
}
