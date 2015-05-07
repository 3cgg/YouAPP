package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.RoleGroup;
import j.jave.framework.servicehub.exception.JServiceException;

import java.util.List;

public interface RoleGroupService extends Service<RoleGroup> {
	
	List<RoleGroup> getRoleGroupsByRoleId(ServiceContext serviceContext,String roleId);
	
	List<RoleGroup> getRoleGroupsByGroupId(ServiceContext serviceContext,String groupId);
	
	/**
	 * tie the role to a group . 
	 * @param serviceContext
	 * @param  roleId
	 * @param groupId
	 */
	void bingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) throws JServiceException;
	
	/**
	 * remove role from the group. 
	 * @param serviceContext
	 * @param  roleId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) throws JServiceException;
	
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
	RoleGroup getRoleGroupOnRoleIdAndGroupId(ServiceContext serviceContext,String roleId,String groupId) throws JServiceException;
	
	
}
