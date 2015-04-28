package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.Role;

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
	
}
