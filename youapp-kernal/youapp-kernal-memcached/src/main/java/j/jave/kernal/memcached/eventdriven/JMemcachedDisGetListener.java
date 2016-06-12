/**
 * 
 */
package j.jave.kernal.memcached.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisGetEvent.class)
public interface JMemcachedDisGetListener extends JYouAPPListener {

	public Object trigger(JMemcachedDisGetEvent event);
	
}
