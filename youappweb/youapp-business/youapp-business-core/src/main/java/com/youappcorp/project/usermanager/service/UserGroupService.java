package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.UserGroup;

public interface UserGroupService extends Service<UserGroup> {
	
	List<UserGroup> getUserGroupsByUserId(ServiceContext serviceContext,String userId);
	
	/**
	 * grant the group to a user. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 */
	void bingUserGroup(ServiceContext serviceContext,String userId,String groupId) throws BusinessException;
	
	/**
	 * remove group on the user. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingUserGroup(ServiceContext serviceContext,String userId,String groupId) throws BusinessException;
	
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
	UserGroup getUserGroupOnUserIdAndGroupId(ServiceContext serviceContext,String userId,String groupId) throws BusinessException;
	
}
