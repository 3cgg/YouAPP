/**
 * 
 */
package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.jave.service.JService;

import java.util.List;


/**
 * system resource interface 
 * @author J
 */
public interface ResourceCacheModelService extends JService{
	
	<T extends ResourceCacheModel> List<T> getResourceCacheModels();
	
}
