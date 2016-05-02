package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.Role;

public interface RoleService extends Service<Role> {

	String ADMIN_CODE="ADMIN";
	
	String DEFAULT_CODE="DEFAULT";
	
	/**
	 * get role by role code. 
	 * @param serviceContext
	 * @param roleCode
	 * @return
	 */
	Role getRoleByRoleCode(ServiceContext serviceContext, String roleCode);
	
	/**
	 * get default ADMIN role. 
	 * @param serviceContext
	 * @return
	 */
	Role getAdminRole(ServiceContext serviceContext);
	
	/**
	 * get default DEFAULT role. 
	 * @param serviceContext
	 * @return
	 */
	Role getDefaultRole(ServiceContext serviceContext);
	
	
	/**
	 * GET ALL ROLES ACCORDING TO 'ROLE NAME'
	 * @param serviceContext
	 * @param pagination
	 * @return
	 */
	JPage<Role> getRoleByRoleNameByPage(ServiceContext serviceContext,JPageable pagination);
	
	/**
	 * GET ALL ROLES.
	 * @param serviceContext
	 * @return
	 */
	List<Role> getAllRoles(ServiceContext serviceContext);
	
	
	/**
	 * add new role, together with validation , including but not restrict 
	 * <p>1. role code must be unique.
	 * @param context 
	 * @param role
	 * @throws JServiceException
	 */
	void saveRole(ServiceContext context, Role role) throws BusinessException;
	
	/**
	 * check if the role is existing, including but not restrict 
	 * <p> 1. role code must be unique.
	 * <p>the method consider the role is updated (primary id is not null )or new created (id is null),
	 * <strong>Note that the primary id( id property ) is the indicator.</strong> 
	 * @param context
	 * @param role
	 * @return
	 * @throws JServiceException
	 */
	boolean exists(ServiceContext context, Role role) throws BusinessException;
	
	/**
	 * update the role , together with validation, including not restrict:
	 * <p>1. role code must be unique.
	 * @param context
	 * @param role
	 * @throws JServiceException
	 */
	void updateRole(ServiceContext context, Role role)throws BusinessException;
	
	/**
	 * delete the role, together with potential validations. 
	 * @param context
	 * @param role
	 * @throws JServiceException
	 */
	void deleteRole(ServiceContext context, Role role)throws BusinessException;
	
}
