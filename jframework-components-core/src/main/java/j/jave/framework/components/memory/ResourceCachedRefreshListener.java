/**
 * 
 */
package j.jave.framework.components.memory;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=ResourceCachedRefreshEvent.class)
public interface ResourceCachedRefreshListener extends JAPPListener {

	public Object trigger(ResourceCachedRefreshEvent event);
	
}
