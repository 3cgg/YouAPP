/**
 * 
 */
package j.jave.kernal.memcached.event;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisGetEvent.class)
public interface JMemcachedDisGetListener extends JYouAPPListener {

	public Object trigger(JMemcachedDisGetEvent event);
	
}
