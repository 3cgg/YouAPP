/**
 * 
 */
package j.jave.framework.commons.memcached.eventdriven;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisDeleteEvent.class)
public interface JMemcachedDisDeleteListener extends JAPPListener {

	public Object trigger(JMemcachedDisDeleteEvent event);
}
