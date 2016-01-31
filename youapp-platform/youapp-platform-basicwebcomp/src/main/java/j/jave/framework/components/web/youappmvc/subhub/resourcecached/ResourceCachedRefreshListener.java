/**
 * 
 */
package j.jave.framework.components.web.youappmvc.subhub.resourcecached;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ResourceCachedRefreshEvent.class)
public interface ResourceCachedRefreshListener extends JAPPListener {

	public Object trigger(ResourceCachedRefreshEvent event);
	
}
