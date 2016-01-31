/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource;

import j.jave.kernal.eventdriven.servicehub.JAPPListener;
import j.jave.kernal.eventdriven.servicehub.JEventOnListener;

/**
 * any sub-class implementation can provide the ResourceCachedService . generally it's used together with ResourceCachedServiceFactory
 * <p><strong>Note that the concrete system must implement the interface if the resource cached function enabled.</strong>
 * @author J
 * @see ResourceCacheServiceFactory
 */
@JEventOnListener(name=ResourceCacheServiceGetEvent.class)
public interface ResourceCacheServiceGetListener extends JAPPListener {
	ResourceCacheService trigger(ResourceCacheServiceGetEvent event); 
}
