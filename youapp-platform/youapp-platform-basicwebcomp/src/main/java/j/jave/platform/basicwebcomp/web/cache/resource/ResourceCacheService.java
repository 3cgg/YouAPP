/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource;

import j.jave.kernal.jave.service.JService;

import java.util.List;


/**
 * system resource interface , to used in request response cached. 
 * @author J
 */
public interface ResourceCacheService extends JService, ResourceCacheServiceGetListener{
	
	List<ResourceCacheModel> getResourceCacheModel();
	
}
