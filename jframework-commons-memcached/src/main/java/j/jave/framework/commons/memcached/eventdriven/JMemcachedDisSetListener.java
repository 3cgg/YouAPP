/**
 * 
 */
package j.jave.framework.commons.memcached.eventdriven;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisSetEvent.class)
public interface JMemcachedDisSetListener extends JAPPListener {

	public Object trigger(JMemcachedDisSetEvent event);
}
