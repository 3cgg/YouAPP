/**
 * 
 */
package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ResourceCacheRefreshEvent.class)
public interface ResourceCacheRefreshListener extends JAPPListener {

	public Object trigger(ResourceCacheRefreshEvent event);
	
}
