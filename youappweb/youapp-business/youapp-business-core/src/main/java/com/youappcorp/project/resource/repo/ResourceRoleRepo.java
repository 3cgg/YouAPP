/**
 * 
 */
package com.youappcorp.project.resource.repo;

import j.jave.kernal.jave.persist.JIPersist;
import com.youappcorp.project.resource.model.ResourceRole;

import java.util.List;

/**
 * @author J
 */
public interface ResourceRoleRepo<T> extends JIPersist<T, ResourceRole> {

	List<ResourceRole> getResourceRolesByResourceId(String resourceId);
	
	int countOnResourceIdAndRoleId(String resourceId,String roleId);
	
	ResourceRole getResourceRoleOnResourceIdAndRoleId(String resourceId,String roleId);
	
}
