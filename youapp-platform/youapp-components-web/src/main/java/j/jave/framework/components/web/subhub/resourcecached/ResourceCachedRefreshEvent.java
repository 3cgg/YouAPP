/**
 * 
 */
package j.jave.framework.components.web.subhub.resourcecached;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;

/**
 * event generated for notifying to refresh cached.
 * @author J
 */
@JListenerOnEvent(name=ResourceCachedRefreshListener.class)
public class ResourceCachedRefreshEvent extends JAPPEvent<ResourceCachedRefreshEvent> {

	private static final long serialVersionUID = -8985852127417839487L;

	public ResourceCachedRefreshEvent(Object source) {
		super(source);
	}

	public ResourceCachedRefreshEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
