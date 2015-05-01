package j.jave.framework.components.login.service;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.UserRole;

import java.util.List;

public interface UserRoleService extends Service<UserRole> {

	
	List<UserRole> getUserRolesByUserId(ServiceContext serviceContext,String userId);
	
	/**
	 * grant the role to a user. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 */
	void bingUserRole(ServiceContext serviceContext,String userId,String roleId) throws ServiceException;
	
	/**
	 * remove role on the user. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 * @throws ServiceException
	 */
	void unbingUserRole(ServiceContext serviceContext,String userId,String roleId) throws ServiceException;
	
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
	UserRole getUserRoleOnUserIdAndRoleId(ServiceContext serviceContext,String userId,String roleId) throws ServiceException;
	
}
