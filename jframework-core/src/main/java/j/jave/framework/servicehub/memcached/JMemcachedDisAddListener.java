/**
 * 
 */
package j.jave.framework.servicehub.memcached;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisAddEvent.class)
public interface JMemcachedDisAddListener extends JAPPListener{

	public Object trigger(JMemcachedDisAddEvent event);
	
}
