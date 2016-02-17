/**
 * 
 */
package j.jave.platform.basicwebcomp.resource.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.resource.model.ResourceGroup;

import java.util.List;

/**
 * @author J
 *
 */
public interface ResourceGroupRepo<T> extends JIPersist<T,ResourceGroup> {

	List<ResourceGroup> getResourceGroupsByResourceId(String resourceId);
	
	int countOnResourceIdAndGroupId(String resourceId,String groupId);
	
	ResourceGroup getResourceGroupOnResourceIdAndGroupId(String resourceId,String groupId);
	
	
}
