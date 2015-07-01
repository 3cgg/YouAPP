/**
 * 
 */
package j.jave.framework.commons.memcached.eventdriven;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisGetEvent.class)
public interface JMemcachedDisGetListener extends JAPPListener {

	public Object trigger(JMemcachedDisGetEvent event);
	
}
