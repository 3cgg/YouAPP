package j.jave.platform.webcomp.web.cache.resource;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.support.resourceuri.InitialResource;

import java.util.Set;

public class ResourceCacheServiceSupportUtil {

	public static Set<InitialResource> getInitialResources(JConfiguration configuration){
		return ResourceCacheServiceSupport.getInitialResources();
	}
}
