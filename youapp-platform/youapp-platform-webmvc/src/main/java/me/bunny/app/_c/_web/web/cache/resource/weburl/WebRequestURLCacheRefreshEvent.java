/**
 * 
 */
package me.bunny.app._c._web.web.cache.resource.weburl;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * event generated for notifying to refresh cached.
 * @author J
 */
@JListenerOnEvent(name=WebRequestURLCacheRefreshListener.class)
public class WebRequestURLCacheRefreshEvent extends JYouAPPEvent<WebRequestURLCacheRefreshEvent> {

	public WebRequestURLCacheRefreshEvent(Object source) {
		super(source);
	}

	public WebRequestURLCacheRefreshEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
