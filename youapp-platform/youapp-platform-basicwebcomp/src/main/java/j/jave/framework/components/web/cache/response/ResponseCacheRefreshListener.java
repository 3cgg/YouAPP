/**
 * 
 */
package j.jave.framework.components.web.cache.response;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ResponseCacheRefreshEvent.class)
public interface ResponseCacheRefreshListener extends JAPPListener {

	public Object trigger(ResponseCacheRefreshEvent event);
	
}
