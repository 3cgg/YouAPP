package j.jave.platform.basicwebcomp.login.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.model.RoleGroup;

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
