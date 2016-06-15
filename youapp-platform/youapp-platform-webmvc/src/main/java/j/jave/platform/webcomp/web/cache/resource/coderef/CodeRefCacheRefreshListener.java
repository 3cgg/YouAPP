/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.coderef;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=CodeRefCacheRefreshEvent.class)
public interface CodeRefCacheRefreshListener extends JYouAPPListener {

	public Object trigger(CodeRefCacheRefreshEvent event);
	
}
