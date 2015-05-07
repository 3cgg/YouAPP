package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.model.JPagination;

import java.util.List;

public interface GroupService extends Service<Group> {

	String ADMIN_CODE="ADMIN";
	
	String DEFAULT_CODE="DEFAULT";
	
	/**
	 * get group by group code. 
	 * @param serviceContext
	 * @param roleCode
	 * @return
	 */
	Group getGroupByGroupCode(ServiceContext serviceContext, String roleCode);
	
	/**
	 * get default ADMIN group. 
	 * @param serviceContext
	 * @return
	 */
	Group getAdminGroup(ServiceContext serviceContext);
	
	/**
	 * get default DEFAULT group. 
	 * @param serviceContext
	 * @return
	 */
	Group getDefaultGroup(ServiceContext serviceContext);
	
	
	/**
	 * GET ALL GROUPS ACCORDING TO 'GROUP NAME'
	 * @param serviceContext
	 * @param pagination
	 * @return
	 */
	List<Group> getGroupByGroupNameByPage(ServiceContext serviceContext,JPagination pagination);
	
	/**
	 * GET ALL GROUPS.
	 * @param serviceContext
	 * @return
	 */
	List<Group> getAllGroups(ServiceContext serviceContext);
	
	
	
	
	
}
