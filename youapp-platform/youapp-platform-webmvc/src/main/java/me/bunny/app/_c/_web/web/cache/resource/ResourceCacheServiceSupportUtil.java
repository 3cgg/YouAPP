package me.bunny.app._c._web.web.cache.resource;

import java.util.Set;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.support.resourceuri.InitialResource;

public class ResourceCacheServiceSupportUtil {

	public static Set<InitialResource> getInitialResources(JConfiguration configuration){
		return ResourceCacheServiceSupport.getInitialResources();
	}
}
