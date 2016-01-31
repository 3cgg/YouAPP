/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.response;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ResponseCacheRefreshEvent.class)
public interface ResponseCacheRefreshListener extends JAPPListener {

	public Object trigger(ResponseCacheRefreshEvent event);
	
}
