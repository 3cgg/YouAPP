/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.coderef;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JEventOnListener(name=CodeRefCacheRefreshEvent.class)
public interface CodeRefCacheRefreshListener extends JYouAPPListener {

	public Object trigger(CodeRefCacheRefreshEvent event);
	
}
