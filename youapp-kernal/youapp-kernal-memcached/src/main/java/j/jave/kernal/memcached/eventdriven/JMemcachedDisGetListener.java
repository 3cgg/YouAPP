/**
 * 
 */
package j.jave.kernal.memcached.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisGetEvent.class)
public interface JMemcachedDisGetListener extends JAPPListener {

	public Object trigger(JMemcachedDisGetEvent event);
	
}
