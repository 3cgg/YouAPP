/**
 * 
 */
package j.jave.framework.components.resource.cache;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ResourceCacheRefreshEvent.class)
public interface ResourceCacheRefreshListener extends JAPPListener {

	public Object trigger(ResourceCacheRefreshEvent event);
	
}
