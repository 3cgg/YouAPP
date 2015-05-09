/**
 * 
 */
package j.jave.framework.servicehub.memcached;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JMemcachedDisAddListener.class)
public class JMemcachedDisAddEvent extends JMemcachedDisEvent<JMemcachedDisAddEvent> {

	/**
	 * @param source
	 * @param priority
	 * @param unique
	 * @param key
	 * @param value
	 * @param expiry
	 * @param type
	 */
	public JMemcachedDisAddEvent(Object source, int priority, String unique,
			String key, Object value, int expiry) {
		super(source, priority, unique, key, value, expiry, TYPE.ADD);
	}

	/**
	 * the expiry set to 0
	 * @param source
	 * @param unique
	 * @param key
	 * @param value
	 */
	public JMemcachedDisAddEvent(Object source, String unique,
			String key, Object value) {
		super(source, JAPPEvent.NORMAL, unique, key, value, 0, TYPE.ADD);
	}
	
	/**
	 * 
	 * @param source
	 * @param unique
	 * @param key
	 * @param value
	 * @param expiry
	 */
	public JMemcachedDisAddEvent(Object source, String unique,
			String key, Object value,int expiry) {
		super(source, JAPPEvent.NORMAL, unique, key, value, expiry, TYPE.ADD);
	}

}
