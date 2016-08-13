package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.service.JService;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.codetable.model.ParamType;
import com.youappcorp.project.usermanager.model.Group;
import com.youappcorp.project.usermanager.model.Role;
import com.youappcorp.project.usermanager.model.RoleGroup;
import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserExtend;
import com.youappcorp.project.usermanager.model.UserGroup;
import com.youappcorp.project.usermanager.model.UserRole;
import com.youappcorp.project.usermanager.vo.GroupSearchCriteria;
import com.youappcorp.project.usermanager.vo.RoleSearchCriteria;
import com.youappcorp.project.usermanager.vo.UserSearchCriteria;


public interface UserManagerService extends JService{

	/**
	 * get user by name 
	 * @param context
	 * @param userName
	 * @return
	 */
	public User getUserByName(ServiceContext context, String userName);

String ADMIN_CODE="ADMIN";
	
	String DEFAULT_CODE="DEFAULT";
	
	/**
	 * get group by group code. 
	 * @param serviceContext
	 * @param roleCode
	 * @return
	 */
	Group getGroupByGroupCode(ServiceContext serviceContext, String groupCode);
	
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
	JPage<Group> getGroupsByPage(ServiceContext serviceContext,
			GroupSearchCriteria groupSearchCriteria, JSimplePageable simplePageable);
	
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
	
	/**
	 * get role by role code. 
	 * @param serviceContext
	 * @param roleCode
	 * @return
	 */
	Role getRoleByRoleCode(ServiceContext serviceContext, String roleCode);
	
	/**
	 * @param serviceContext
	 * @param id
	 * @return
	 */
	Role getRoleById(ServiceContext serviceContext, String id);
	
	
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
	
	
	JPage<ParamType> getAllRolesByPage(ServiceContext context,RoleSearchCriteria roleSearchCriteria,JSimplePageable simplePageable);
	
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
	
	/**
	 * get user by name & password 
	 * @param userName
	 * @param password
	 * @return
	 */
	public User getUserByNameAndPassword(String userName,String password);
	
	/**
	 * 
	 * @param context 
	 * @param user
	 * @throws JServiceException
	 */
	public void saveUser(ServiceContext context, User user) throws BusinessException;
	
	
	/**
	 * 
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void updateUser(ServiceContext context, User user) throws BusinessException;

	/**
	 * search user 
	 * @param context
	 * @param simplePageable
	 * @return
	 */
	public JPage<User> getUsersByPage(ServiceContext context,UserSearchCriteria userSearchCriteria, 
			JSimplePageable simplePageable) ;
	
	/**
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public User getUserById(ServiceContext context, String id);
	
	/**
	 * all users (not deleted) .
	 * @return
	 */
	public List<User> getUsers(ServiceContext context);
	
	/**
	 * register a user from views. its a component that wraps the logic related. 
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void register(ServiceContext context,User user,UserExtend userExtend) throws BusinessException;
	
	/**
	 * @param userId
	 * @param context
	 * @param password
	 */
	public void resetPassword(ServiceContext context,String userId,String password);
	
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
	boolean isBingRoleAndGroup(ServiceContext serviceContext,String roleId,String groupId);
	
	/**
	 * GET ROLE GROUP. 
	 * @param serviceContext
	 *  @param  roleId
	 * @param groupId
	 * @return
	 */
	RoleGroup getRoleGroupOnRoleIdAndGroupId(ServiceContext serviceContext,String roleId,String groupId);
	
	/**
	 * GET ROLE GROUP. 
	 * @param serviceContext
	 *  @param  roleId
	 * @param groupId
	 * @return
	 */
	long countOnRoleIdAndGroupId(ServiceContext serviceContext,String roleId,String groupId);
	
	
	List<UserRole> getUserRolesByUserId(ServiceContext serviceContext,String userId);
	
	/**
	 * grant the role to a user. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 */
	void bingUserRole(ServiceContext serviceContext,String userId,String roleId) throws BusinessException;
	
	/**
	 * remove role on the user. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 * @throws JServiceException
	 */
	void unbingUserRole(ServiceContext serviceContext,String userId,String roleId) throws BusinessException;
	
	/**
	 * check if the user has the role. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 * @return
	 */
	boolean isBingUserAndRole(ServiceContext serviceContext,String userId,String roleId);
	
	/**
	 * GET USER ROLE. 
	 * @param serviceContext
	 * @param userId
	 * @param roleId
	 * @return
	 */
	UserRole getUserRoleOnUserIdAndRoleId(ServiceContext serviceContext,String userId,String roleId);
	
	
	/**
	 * save user extension , that is always called by UserService. including some validations:
	 * <p>1. user id is not null
	 * <p>2. user name is not null
	 * <p>3. user nature name is unique in the system
	 * @param context 
	 * @param userExtend
	 * @throws JServiceException
	 */
	public void saveUserExtend(ServiceContext context, UserExtend userExtend) throws BusinessException;
	
	
	/**
	 * update user extension,including some validations:
	 * <p>1. user id is not null
	 * <p>2. user name is not null
	 * <p>3. user nature name is unique in the system
	 * @param context
	 * @param userExtend
	 * @throws JServiceException
	 */
	public void updateUserExtend(ServiceContext context, UserExtend userExtend) throws BusinessException;
	
	/**
	 * get user extension by user id that is the primary key in the user model.
	 * @param context
	 * @param userId
	 * @return
	 * @see User
	 */
	public UserExtend getUserExtendByUserId(ServiceContext context, String userId);
	
	/**
	 * get user extension by nature name . the nature name is also unique in the system.
	 * @param context
	 * @param natureName  unique 
	 * @return
	 */
	public UserExtend getUserExtendByNatureName(ServiceContext context, String natureName);
	
	
	List<UserGroup> getUserGroupsByUserId(ServiceContext serviceContext,String userId);
	
	/**
	 * grant the group to a user. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 */
	void bingUserGroup(ServiceContext serviceContext,String userId,String groupId) throws BusinessException;
	
	/**
	 * remove group on the user. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingUserGroup(ServiceContext serviceContext,String userId,String groupId) throws BusinessException;
	
	/**
	 * check if the user belongs to the group. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 * @return
	 */
	boolean isBingUserAndGroup(ServiceContext serviceContext,String userId,String groupId);
	
	/**
	 * GET USER GROUP. 
	 * @param serviceContext
	 * @param userId
	 * @param groupId
	 * @return
	 */
	UserGroup getUserGroupOnUserIdAndGroupId(ServiceContext serviceContext,String userId,String groupId) throws BusinessException;
	
}
