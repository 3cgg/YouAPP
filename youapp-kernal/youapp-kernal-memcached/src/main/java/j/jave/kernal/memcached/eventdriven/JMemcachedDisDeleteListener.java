/**
 * 
 */
package j.jave.kernal.memcached.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisDeleteEvent.class)
public interface JMemcachedDisDeleteListener extends JAPPListener {

	public Object trigger(JMemcachedDisDeleteEvent event);
}
