package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.webcomp.core.service.Service;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.Group;

public interface GroupService extends Service<Group, String> {

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
	JPage<Group> getGroupByGroupNameByPage(ServiceContext serviceContext,JPageable pagination);
	
	/**
	 * GET ALL GROUPS.
	 * @param serviceContext
	 * @return
	 */
	List<Group> getAllGroups(ServiceContext serviceContext);
	
	
	/**
	 * add new group, together with validation , including but not restrict 
	 * <p>1. group code must be unique.
	 * @param context 
	 * @param group
	 * @throws JServiceException
	 */
	public void saveGroup(ServiceContext context, Group group) throws BusinessException;
	
	/**
	 *  check if the group is existing, including but not restrict 
	 * <p> 1. group code must be unique.
	 * <p>the method consider the role is updated (primary id is not null )or new created (id is null),
	 * <strong>Note that the primary id( id property ) is the indicator.</strong> 
	 * @param context
	 * @param group
	 * @return
	 * @throws JServiceException
	 */
	boolean exists(ServiceContext context, Group group) throws BusinessException;
	
	
	/**
	 * update the group , together with validation, including not restrict:
	 * <p>1. group code must be unique.
	 * @param context
	 * @param group
	 * @throws JServiceException
	 */
	void updateGroup(ServiceContext context, Group group)throws BusinessException;
	
	/**
	 * delete the group, together with potential validations. 
	 * @param context
	 * @param group
	 * @throws JServiceException
	 */
	void deleteGroup(ServiceContext context, Group group)throws BusinessException;
	
}
