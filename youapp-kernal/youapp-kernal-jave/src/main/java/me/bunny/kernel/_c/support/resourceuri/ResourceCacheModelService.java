/**
 * 
 */
package me.bunny.kernel._c.support.resourceuri;

import java.util.List;

import me.bunny.kernel._c.service.JService;


/**
 * system resource interface 
 * @author J
 */
public interface ResourceCacheModelService extends JService{
	
	<T extends ResourceCacheModel> List<T> getResourceCacheModels();
	
}
