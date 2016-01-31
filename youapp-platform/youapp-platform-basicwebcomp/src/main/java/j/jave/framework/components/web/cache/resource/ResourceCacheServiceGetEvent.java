/**
 * 
 */
package j.jave.framework.components.web.cache.resource;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;

/**
 * resource cached get event.
 * @author J
 */
@JListenerOnEvent(name=ResourceCacheServiceGetListener.class)
public class ResourceCacheServiceGetEvent extends JAPPEvent<ResourceCacheServiceGetEvent> {

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
