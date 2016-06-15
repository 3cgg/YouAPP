/**
 * 
 */
package j.jave.platform.webcomp.web.cache.response;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ResponseCacheRefreshEvent.class)
public interface ResponseCacheRefreshListener extends JYouAPPListener {

	public Object trigger(ResponseCacheRefreshEvent event);
	
}
