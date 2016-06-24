package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.webcomp.core.service.InternalService;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.RoleGroup;

public interface RoleGroupService extends InternalService<RoleGroup, String> {
	
	List<RoleGroup> getRoleGroupsByRoleId(ServiceContext serviceContext,String roleId);
	
	List<RoleGroup> getRoleGroupsByGroupId(ServiceContext serviceContext,String groupId);
	
	/**
	 * tie the role to a group . 
	 * @param serviceContext
	 * @param  roleId
	 * @param groupId
	 */
	void bingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) throws BusinessException;
	
	/**
	 * remove role from the group. 
	 * @param serviceContext
	 * @param  roleId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) throws BusinessException;
	
	/**
	 * check if the role belongs to the group. 
	 * @param serviceContext
	 *  @param  roleId
	 * @param groupId
	 * @return
	 */
	boolean isBing(ServiceContext serviceContext,String roleId,String groupId);
	
	/**
	 * GET ROLE GROUP. 
	 * @param serviceContext
	 *  @param  roleId
	 * @param groupId
	 * @return
	 */
	RoleGroup getRoleGroupOnRoleIdAndGroupId(ServiceContext serviceContext,String roleId,String groupId);
	
	
}
