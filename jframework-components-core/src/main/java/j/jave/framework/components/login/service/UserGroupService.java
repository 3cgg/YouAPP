package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.UserGroup;
import j.jave.framework.servicehub.exception.JServiceException;

import java.util.List;

public interface UserGroupService extends Service<UserGroup> {
	
	List<UserGroup> getUserGroupsByUserId(ServiceContext serviceContext,String userId);
	
	/**
	 * grant the group to a user. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 */
	void bingUserGroup(ServiceContext serviceContext,String userId,String groupId) throws JServiceException;
	
	/**
	 * remove group on the user. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingUserGroup(ServiceContext serviceContext,String userId,String groupId) throws JServiceException;
	
	/**
	 * check if the user belongs to the group. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 * @return
	 */
	boolean isBing(ServiceContext serviceContext,String userId,String groupId);
	
	/**
	 * GET USER GROUP. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 * @return
	 */
	UserGroup getUserGroupOnUserIdAndGroupId(ServiceContext serviceContext,String userId,String groupId) throws JServiceException;
	
}
