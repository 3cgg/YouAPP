/**
 * 
 */
package j.jave.platform.basicwebcomp.resource.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.resource.model.Resource;

import java.util.List;

/**
 * 
 * @author JIAZJ
 *
 */
public interface ResourceRepo<T> extends JIPersist<T, Resource> {
	
	List<Resource> getResources();
	
	Resource getResourceByURL(String url);
	
}
