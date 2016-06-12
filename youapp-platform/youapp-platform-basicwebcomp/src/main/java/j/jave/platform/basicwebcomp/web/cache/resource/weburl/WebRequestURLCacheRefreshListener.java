/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.weburl;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=WebRequestURLCacheRefreshEvent.class)
public interface WebRequestURLCacheRefreshListener extends JYouAPPListener {

	public Object trigger(WebRequestURLCacheRefreshEvent event);
	
}
