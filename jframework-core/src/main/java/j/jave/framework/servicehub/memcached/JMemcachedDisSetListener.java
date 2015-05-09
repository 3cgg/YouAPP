/**
 * 
 */
package j.jave.framework.servicehub.memcached;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisSetEvent.class)
public interface JMemcachedDisSetListener extends JAPPListener {

	public Object trigger(JMemcachedDisSetEvent event);
}
