/**
 * 
 */
package j.jave.framework.components.web.subhub.resourcecached;

import j.jave.framework.commons.service.JService;

import java.util.List;


/**
 * system resource interface , to used in request response cached. 
 * concrete need do only one implementation to this class.
 * @author J
 */
public interface ResourceCachedService extends JService, ResourceCachedServiceGetListener{
	
	List<ResourceCached> getResourceCached();
	
}
