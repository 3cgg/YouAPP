/**
 * 
 */
package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * resource cached get event.
 * @author J
 */
@JListenerOnEvent(name=ResourceCacheServiceGetListener.class)
public class ResourceCacheServiceGetEvent extends JYouAPPEvent<ResourceCacheServiceGetEvent> {

	/**
	 * @param source
	 * @param priority
	 */
	public ResourceCacheServiceGetEvent(Object source, int priority) {
		super(source, priority);
	}

	/**
	 * @param source
	 * @param priority
	 * @param unique
	 */
	public ResourceCacheServiceGetEvent(Object source, int priority, String unique) {
		super(source, priority, unique);
	}
	
	
		/**
	 * @param source
	 */
	public ResourceCacheServiceGetEvent(Object source) {
		super(source);
	}



	
	
	
}
