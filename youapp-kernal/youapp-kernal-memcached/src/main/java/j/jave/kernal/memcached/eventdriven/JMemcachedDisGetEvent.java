/**
 * 
 */
package j.jave.kernal.memcached.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;


/**
 * @author J
 */
@JListenerOnEvent(name=JMemcachedDisGetListener.class)
public class JMemcachedDisGetEvent extends JMemcachedDisEvent<JMemcachedDisGetEvent> {

	public JMemcachedDisGetEvent(Object source, String key) {
		super(source, key, null, 0, TYPE.GET);
	}

}
