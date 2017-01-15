/**
 * 
 */
package me.bunny.kernel.jave.support.resourceuri;

import java.util.List;

import me.bunny.kernel.jave.service.JService;


/**
 * system resource interface 
 * @author J
 */
public interface ResourceCacheModelService extends JService{
	
	<T extends ResourceCacheModel> List<T> getResourceCacheModels();
	
}
