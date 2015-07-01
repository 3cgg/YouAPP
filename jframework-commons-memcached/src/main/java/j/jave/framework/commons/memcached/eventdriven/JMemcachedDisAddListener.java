/**
 * 
 */
package j.jave.framework.commons.memcached.eventdriven;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisAddEvent.class)
public interface JMemcachedDisAddListener extends JAPPListener{

	public Object trigger(JMemcachedDisAddEvent event);
	
}
