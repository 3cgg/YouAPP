/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import j.jave.kernal.jave.persist.JIPersist;

import java.util.List;

import com.youappcorp.project.usermanager.model.UserRole;

/**
 * @author J
 *
 */
public interface UserRoleRepo<T> extends JIPersist<T,UserRole,String> {

	List<UserRole> getUserRolesByUserId(String userId);
	
	int countOnUserIdAndRoleId(String userId,String roleId);
	
	UserRole getUserRoleOnUserIdAndRoleId(String userId,String roleId);
}
