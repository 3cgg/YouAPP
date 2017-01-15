/**
 * 
 */
package j.jave.kernal.memcached.event;

import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;


/**
 * notify the memcache system to store .
 * @author J
 */
public abstract class JMemcachedDisEvent<T extends JMemcachedDisEvent<T>> extends JYouAPPEvent<JMemcachedDisEvent<T>>{

	public enum TYPE{
		/**
		 * get object from the cache.
		 */
		GET,
		/**
		 * add object into the cache.
		 */
		ADD,
		/**
		 * replace the existing object in the cache.
		 */
		SET,
		/**
		 * delete the object from the cache.
		 */
		DELETE
	}
	
	private String key;
	
	private Object value;
	
	private int expiry;
	
	private TYPE type;
	
	public JMemcachedDisEvent(Object source, int priority, String unique, String key,Object value,int expiry,TYPE type) {
		super(source, priority, unique);
		this.key=key;
		this.value=value;
		this.expiry=expiry;
		this.type=type;
	}
	
	public JMemcachedDisEvent(Object source,String key,Object value,int expiry,TYPE type) {
		super(source);
		this.key=key;
		this.value=value;
		this.expiry=expiry;
		this.type=type;
	}
	
	public JMemcachedDisEvent(Object source, int priority,String key,Object value,int expiry,TYPE type) {
		super(source, priority);
		this.key=key;
		this.value=value;
		this.expiry=expiry;
		this.type=type;
		this.type=type;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public int getExpiry() {
		return expiry;
	}
	
	/**
	 * @return the type
	 */
	public TYPE getType() {
		return type;
	}

}
