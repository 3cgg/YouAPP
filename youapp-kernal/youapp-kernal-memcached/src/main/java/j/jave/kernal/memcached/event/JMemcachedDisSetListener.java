/**
 * 
 */
package j.jave.kernal.memcached.event;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisSetEvent.class)
public interface JMemcachedDisSetListener extends JYouAPPListener {

	public Object trigger(JMemcachedDisSetEvent event);
}
