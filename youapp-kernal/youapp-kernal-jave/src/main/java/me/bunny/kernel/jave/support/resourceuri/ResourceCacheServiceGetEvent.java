/**
 * 
 */
package me.bunny.kernel.jave.support.resourceuri;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

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
