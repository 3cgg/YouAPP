/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.weburl;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * event generated for notifying to refresh cached.
 * @author J
 */
@JListenerOnEvent(name=WebRequestURLCacheRefreshListener.class)
public class WebRequestURLCacheRefreshEvent extends JAPPEvent<WebRequestURLCacheRefreshEvent> {

	public WebRequestURLCacheRefreshEvent(Object source) {
		super(source);
	}

	public WebRequestURLCacheRefreshEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
