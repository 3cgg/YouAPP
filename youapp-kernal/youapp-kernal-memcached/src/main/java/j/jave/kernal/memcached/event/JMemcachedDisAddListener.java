/**
 * 
 */
package j.jave.kernal.memcached.event;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisAddEvent.class)
public interface JMemcachedDisAddListener extends JYouAPPListener{

	public Object trigger(JMemcachedDisAddEvent event);
	
}
