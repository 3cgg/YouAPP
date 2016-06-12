/**
 * 
 */
package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ResourceCacheRefreshEvent.class)
public interface ResourceCacheRefreshListener extends JYouAPPListener {

	public Object trigger(ResourceCacheRefreshEvent event);
	
}
