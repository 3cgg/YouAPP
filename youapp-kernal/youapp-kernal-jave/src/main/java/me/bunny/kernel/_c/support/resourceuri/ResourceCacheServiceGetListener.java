/**
 * 
 */
package me.bunny.kernel._c.support.resourceuri;

import me.bunny.kernel.eventdriven.servicehub.JEventOnListener;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * any sub-class implementation can provide the ResourceCachedService . generally it's used together with ResourceCachedServiceFactory
 * <p><strong>Note that the concrete system must implement the interface if the resource cached function enabled.</strong>
 * @author J
 * @see WebRequestURLCacheServiceFactory
 */
@JEventOnListener(name=ResourceCacheServiceGetEvent.class)
public interface ResourceCacheServiceGetListener extends JYouAPPListener {
	ResourceCacheService trigger(ResourceCacheServiceGetEvent event); 
}
