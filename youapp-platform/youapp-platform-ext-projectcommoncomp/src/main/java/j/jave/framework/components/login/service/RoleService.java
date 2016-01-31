package j.jave.framework.components.login.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.model.JPagination;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.Role;

import java.util.List;

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
	List<Role> getRoleByRoleNameByPage(ServiceContext serviceContext,JPagination pagination);
	
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
	void saveRole(ServiceContext context, Role role) throws JServiceException;
	
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
	boolean exists(ServiceContext context, Role role) throws JServiceException;
	
	/**
	 * update the role , together with validation, including not restrict:
	 * <p>1. role code must be unique.
	 * @param context
	 * @param role
	 * @throws JServiceException
	 */
	void updateRole(ServiceContext context, Role role)throws JServiceException;
	
	/**
	 * delete the role, together with potential validations. 
	 * @param context
	 * @param role
	 * @throws JServiceException
	 */
	void deleteRole(ServiceContext context, Role role)throws JServiceException;
	
}
