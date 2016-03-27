/**
 * 
 */
package com.youappcorp.project.resource.repo;

import j.jave.kernal.jave.persist.JIPersist;
import com.youappcorp.project.resource.model.ResourceExtend;

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
