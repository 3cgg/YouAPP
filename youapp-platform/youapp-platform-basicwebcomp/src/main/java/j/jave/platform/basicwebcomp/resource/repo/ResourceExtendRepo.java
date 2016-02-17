/**
 * 
 */
package j.jave.platform.basicwebcomp.resource.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.resource.model.ResourceExtend;

import java.util.List;

/**
 * @author J
 *
 */
public interface ResourceExtendRepo<T> extends JIPersist<T, ResourceExtend> {

	void updateCached(String id,String cached);
	
	ResourceExtend getResourceExtendOnResourceId(String resourceId);
	
	List<ResourceExtend> getAllResourceExtends();
}
