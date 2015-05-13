/**
 * 
 */
package j.jave.framework.components.web.subhub.resourcecached;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;

/**
 * any sub-class implementation can provide the ResourceCachedService . generally it's used together with ResourceCachedServiceFactory
 * <p><strong>Note that the concrete system must implement the interface if the resource cached function enabled.</strong>
 * @author J
 * @see ResourceCachedServiceFactory
 */
@JEventOnListener(name=ResourceCachedServiceGetEvent.class)
public interface ResourceCachedServiceGetListener extends JAPPListener {
	ResourceCachedService trigger(ResourceCachedServiceGetEvent event); 
}
