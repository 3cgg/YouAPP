/**
 * 
 */
package j.jave.framework.servicehub.memcached;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisDeleteEvent.class)
public interface JMemcachedDisDeleteListener extends JAPPListener {

	public Object trigger(JMemcachedDisDeleteEvent event);
}
