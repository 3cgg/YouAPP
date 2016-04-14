package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.usermanager.model.UserRole;

public interface UserRoleService extends Service<UserRole> {

	
	List<UserRole> getUserRolesByUserId(ServiceContext serviceContext,String userId);
	
	/**
	 * grant the role to a user. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 */
	void bingUserRole(ServiceContext serviceContext,String userId,String roleId) throws JServiceException;
	
	/**
	 * remove role on the user. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 * @throws JServiceException
	 */
	void unbingUserRole(ServiceContext serviceContext,String userId,String roleId) throws JServiceException;
	
	/**
	 * check if the user has the role. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 * @return
	 */
	boolean isBing(ServiceContext serviceContext,String userId,String roleId);
	
	/**
	 * GET USER ROLE. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 * @return
	 */
	UserRole getUserRoleOnUserIdAndRoleId(ServiceContext serviceContext,String userId,String roleId) throws JServiceException;
	
}