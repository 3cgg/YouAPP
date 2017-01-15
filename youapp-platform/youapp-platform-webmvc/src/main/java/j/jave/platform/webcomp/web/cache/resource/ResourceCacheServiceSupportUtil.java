package j.jave.platform.webcomp.web.cache.resource;

import java.util.Set;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.support.resourceuri.InitialResource;

public class ResourceCacheServiceSupportUtil {

	public static Set<InitialResource> getInitialResources(JConfiguration configuration){
		return ResourceCacheServiceSupport.getInitialResources();
	}
}
