/**
 * 
 */
package j.jave.framework.commons.memcached.eventdriven;

import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;


/**
 * @author J
 */
@JListenerOnEvent(name=JMemcachedDisDeleteListener.class)
public class JMemcachedDisDeleteEvent extends JMemcachedDisEvent<JMemcachedDisDeleteEvent> {

	public JMemcachedDisDeleteEvent(Object source, String key) {
		super(source, key, null, 0, TYPE.DELETE);
	}

}
