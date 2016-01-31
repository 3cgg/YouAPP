/**
 * 
 */
package j.jave.framework.components.web.cache.response;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;

/**
 * event generated for notifying to refresh cached.
 * @author J
 */
@JListenerOnEvent(name=ResponseCacheRefreshListener.class)
public class ResponseCacheRefreshEvent extends JAPPEvent<ResponseCacheRefreshEvent> {

	private static final long serialVersionUID = -8985852127417839487L;

	public ResponseCacheRefreshEvent(Object source) {
		super(source);
	}

	public ResponseCacheRefreshEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
