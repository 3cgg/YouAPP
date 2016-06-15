/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.weburl;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

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
