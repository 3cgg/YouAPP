/**
 * 
 */
package j.jave.kernal.memcached.event;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisDeleteEvent.class)
public interface JMemcachedDisDeleteListener extends JYouAPPListener {

	public Object trigger(JMemcachedDisDeleteEvent event);
}
