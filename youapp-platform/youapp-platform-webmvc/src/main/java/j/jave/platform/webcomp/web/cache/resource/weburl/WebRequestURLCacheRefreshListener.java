/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.weburl;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=WebRequestURLCacheRefreshEvent.class)
public interface WebRequestURLCacheRefreshListener extends JYouAPPListener {

	public Object trigger(WebRequestURLCacheRefreshEvent event);
	
}
