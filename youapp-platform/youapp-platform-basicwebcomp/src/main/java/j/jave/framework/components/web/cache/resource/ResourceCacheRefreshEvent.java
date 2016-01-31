/**
 * 
 */
package j.jave.framework.components.web.cache.resource;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;

/**
 * event generated for notifying to refresh cached.
 * @author J
 */
@JListenerOnEvent(name=ResourceCacheRefreshListener.class)
public class ResourceCacheRefreshEvent extends JAPPEvent<ResourceCacheRefreshEvent> {

	private static final long serialVersionUID = -8985852127417839487L;

	public ResourceCacheRefreshEvent(Object source) {
		super(source);
	}

	public ResourceCacheRefreshEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
