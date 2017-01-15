/**
 * 
 */
package j.jave.kernal.memcached.event;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisAddEvent.class)
public interface JMemcachedDisAddListener extends JYouAPPListener{

	public Object trigger(JMemcachedDisAddEvent event);
	
}
