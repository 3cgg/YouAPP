/**
 * 
 */
package j.jave.kernal.memcached.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JYouAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisSetEvent.class)
public interface JMemcachedDisSetListener extends JYouAPPListener {

	public Object trigger(JMemcachedDisSetEvent event);
}
