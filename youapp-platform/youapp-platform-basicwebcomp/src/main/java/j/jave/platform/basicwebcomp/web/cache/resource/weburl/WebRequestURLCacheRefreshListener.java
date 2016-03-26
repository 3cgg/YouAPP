/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.weburl;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=WebRequestURLCacheRefreshEvent.class)
public interface WebRequestURLCacheRefreshListener extends JAPPListener {

	public Object trigger(WebRequestURLCacheRefreshEvent event);
	
}
