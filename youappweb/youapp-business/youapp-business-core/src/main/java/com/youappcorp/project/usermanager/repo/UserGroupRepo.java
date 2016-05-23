/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import j.jave.kernal.jave.persist.JIPersist;

import java.util.List;

import com.youappcorp.project.usermanager.model.UserGroup;

/**
 * @author J
 *
 */
public interface UserGroupRepo<T> extends JIPersist<T,UserGroup,String> {
	
	List<UserGroup> getUserGroupsByUserId(String userId);
	
	int countOnUserIdAndGroupId(String userId,String groupId);
	
	UserGroup getUserGroupOnUserIdAndGroupId(String userId,String groupId);
	
}
