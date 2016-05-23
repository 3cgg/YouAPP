/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import j.jave.kernal.jave.persist.JIPersist;

import java.util.List;

import com.youappcorp.project.usermanager.model.RoleGroup;

/**
 * @author J
 *
 */
public interface RoleGroupRepo<T> extends JIPersist<T,RoleGroup,String> {
	
	int countOnRoleIdAndGroupId(String roleId,String groupId);
	
	RoleGroup getRoleGroupOnRoleIdAndGroupId(String roleId,String groupId);
	
	List<RoleGroup> getRoleGroupsOnRoleIdOrGroupId(String roleId,String groupId);
	
	
}
