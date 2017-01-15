/**
 * 
 */
package j.jave.platform.webcomp.web.cache.response;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

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
