/**
 * 
 */
package j.jave.framework.servicehub.memcached;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisGetEvent.class)
public interface JMemcachedDisGetListener extends JAPPListener {

	public Object trigger(JMemcachedDisGetEvent event);
	
}
