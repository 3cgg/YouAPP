/**
 * 
 */
package j.jave.kernal.memcached.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;


/**
 * @author J
 */
@JEventOnListener(name=JMemcachedDisAddEvent.class)
public interface JMemcachedDisAddListener extends JAPPListener{

	public Object trigger(JMemcachedDisAddEvent event);
	
}
