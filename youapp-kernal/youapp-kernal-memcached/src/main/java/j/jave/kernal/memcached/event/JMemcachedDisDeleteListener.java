/**
 * 
 */
package j.jave.kernal.memcached.event;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisDeleteEvent.class)
public interface JMemcachedDisDeleteListener extends JYouAPPListener {

	public Object trigger(JMemcachedDisDeleteEvent event);
}
