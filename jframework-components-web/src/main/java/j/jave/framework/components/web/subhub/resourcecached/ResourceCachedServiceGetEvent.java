/**
 * 
 */
package j.jave.framework.components.web.subhub.resourcecached;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;

/**
 * resource cached get event.
 * @author J
 */
@JListenerOnEvent(name=ResourceCachedServiceGetListener.class)
public class ResourceCachedServiceGetEvent extends JAPPEvent<ResourceCachedServiceGetEvent> {

	/**
	 * @param source
	 * @param priority
	 */
	public ResourceCachedServiceGetEvent(Object source, int priority) {
		super(source, priority);
	}

	/**
	 * @param source
	 * @param priority
	 * @param unique
	 */
	public ResourceCachedServiceGetEvent(Object source, int priority, String unique) {
		super(source, priority, unique);
	}
	
	
		/**
	 * @param source
	 */
	public ResourceCachedServiceGetEvent(Object source) {
		super(source);
	}



	
	
	
}
