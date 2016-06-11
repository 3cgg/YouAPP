/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.response;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * event generated for notifying to refresh cached.
 * @author J
 */
@JListenerOnEvent(name=ResponseCacheRefreshListener.class)
public class ResponseCacheRefreshEvent extends JYouAPPEvent<ResponseCacheRefreshEvent> {

	private static final long serialVersionUID = -8985852127417839487L;

	public ResponseCacheRefreshEvent(Object source) {
		super(source);
	}

	public ResponseCacheRefreshEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
