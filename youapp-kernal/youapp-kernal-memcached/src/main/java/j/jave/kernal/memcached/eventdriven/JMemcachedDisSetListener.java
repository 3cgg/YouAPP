/**
 * 
 */
package j.jave.kernal.memcached.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisSetEvent.class)
public interface JMemcachedDisSetListener extends JAPPListener {

	public Object trigger(JMemcachedDisSetEvent event);
}
