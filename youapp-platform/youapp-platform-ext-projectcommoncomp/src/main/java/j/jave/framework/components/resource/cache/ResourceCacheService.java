/**
 * 
 */
package j.jave.framework.components.resource.cache;

import j.jave.framework.commons.service.JService;

import java.util.List;


/**
 * system resource interface , to used in request response cached. 
 * @author J
 */
public interface ResourceCacheService extends JService, ResourceCacheServiceGetListener{
	
	List<ResourceCacheModel> getResourceCacheModel();
	
}
