package com.youappcorp.project.usermanager.service;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.Group;
import com.youappcorp.project.usermanager.model.GroupRecord;
import com.youappcorp.project.usermanager.model.Role;
import com.youappcorp.project.usermanager.model.RoleGroup;
import com.youappcorp.project.usermanager.model.RoleRecord;
import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserDetail;
import com.youappcorp.project.usermanager.model.UserExtend;
import com.youappcorp.project.usermanager.model.UserGroup;
import com.youappcorp.project.usermanager.model.UserRecord;
import com.youappcorp.project.usermanager.model.UserRole;
import com.youappcorp.project.usermanager.vo.GroupSearchCriteria;
import com.youappcorp.project.usermanager.vo.RoleSearchCriteria;
import com.youappcorp.project.usermanager.vo.UserSearchCriteria;

import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.exception.JServiceException;


public interface UserManagerService extends JService{

	/**
	 * get user by name 
	 * 
	 * @param userName
	 * @return
	 */
	public User getUserByName( String userName);
	
	/**
	 * get more detail information as possible
	 * 
	 * @param userName
	 * @return
	 */
	public UserDetail getUserDetailByName( String userName);

	/**
	 * get more detail information as possible
	 * 
	 * @param id
	 * @return
	 */
	public UserDetail getUserDetailById( String id);
	
	String ADMIN_CODE="ADMIN";
	
	String DEFAULT_CODE="DEFAULT";
	
	/**
	 * get group by group code. 
	 * 
	 * @param roleCode
	 * @return
	 */
	Group getGroupByGroupCode( String groupCode);
	
	/**
	 * get group by primary id.
	 * 
	 * @param id
	 * @return
	 */
	Group getGroupById( String id);
	
	
	/**
	 * get default ADMIN group. 
	 * 
	 * @return
	 */
	Group getAdminGroup();
	
	/**
	 * get default DEFAULT group. 
	 * 
	 * @return
	 */
	Group getDefaultGroup();
	
	boolean isDefaultGroup(String groupId);
	
	boolean isDefaultGroupCode(String code);
	
	boolean isAdminGroup(String groupId);
	
	boolean isAdminGroupCode(String code);
	
	
	/**
	 * GET ALL GROUPS ACCORDING TO 'GROUP NAME'
	 * 
	 * @param pagination
	 * @return
	 */
	JPage<Group> getGroupsByPage(
			GroupSearchCriteria groupSearchCriteria, JSimplePageable simplePageable);
	
	List<GroupRecord> getGroupsByRoleId(String roleId);
	
	JPage<GroupRecord> getGroupsByRoleIdByPage(String roleId,JSimplePageable simplePageable);
	
	List<GroupRecord> getUnbingGroupsByRoleId(String roleId);
	
	JPage<GroupRecord> getUnbingGroupsByRoleIdByPage(String roleId,JSimplePageable simplePageable);
	
	JPage<UserRecord> getUnbingUsersByRoleIdByPage(String roleId,JSimplePageable simplePageable);
	
	JPage<UserRecord> getUnbingUsersByGroupIdByPage(String groupId,JSimplePageable simplePageable);
	
	
	/**
	 * GET ALL GROUPS.
	 * 
	 * @return
	 */
	List<Group> getAllGroups();
	
	
	/**
	 * add new group, together with validation , including but not restrict 
	 * <p>1. group code must be unique.
	 *  
	 * @param group
	 * @throws JServiceException
	 */
	public void saveGroup( Group group) throws BusinessException;
	
	/**
	 *  check if the group is existing, including but not restrict 
	 * <p> 1. group code must be unique.
	 * <p>the method consider the role is updated (primary id is not null )or new created (id is null),
	 * <strong>Note that the primary id( id property ) is the indicator.</strong> 
	 * 
	 * @param group
	 * @return
	 * @throws JServiceException
	 */
	boolean exists( Group group) throws BusinessException;
	
	
	/**
	 * update the group , together with validation, including not restrict:
	 * <p>1. group code must be unique.
	 * 
	 * @param group
	 * @throws JServiceException
	 */
	void updateGroup( Group group)throws BusinessException;
	
	/**
	 * delete the group, together with potential validations. 
	 * 
	 * @param group
	 * @throws JServiceException
	 */
	void deleteGroup( Group group)throws BusinessException;
	
	/**
	 * get role by role code. 
	 * 
	 * @param roleCode
	 * @return
	 */
	Role getRoleByRoleCode( String roleCode);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Role getRoleById( String id);
	
	
	/**
	 * get default ADMIN role. 
	 * 
	 * @return
	 */
	Role getAdminRole();
	
	boolean isAdminRole(String roleId);
	
	boolean isAdminRoleCode(String code);
	
	/**
	 * get default DEFAULT role. 
	 * 
	 * @return
	 */
	Role getDefaultRole();
	
	boolean isDefaultRole(String roleId);
	
	boolean isDefaultRoleCode(String code);
	
	JPage<Role> getAllRolesByPage(RoleSearchCriteria roleSearchCriteria,JSimplePageable simplePageable);
	
	/**
	 * GET ALL ROLES.
	 * 
	 * @return
	 */
	List<Role> getAllRoles();
	
	
	/**
	 * add new role, together with validation , including but not restrict 
	 * <p>1. role code must be unique.
	 *  
	 * @param role
	 * @throws JServiceException
	 */
	void saveRole( Role role) throws BusinessException;
	
	/**
	 * check if the role is existing, including but not restrict 
	 * <p> 1. role code must be unique.
	 * <p>the method consider the role is updated (primary id is not null )or new created (id is null),
	 * <strong>Note that the primary id( id property ) is the indicator.</strong> 
	 * 
	 * @param role
	 * @return
	 * @throws JServiceException
	 */
	boolean exists( Role role) throws BusinessException;
	
	/**
	 * update the role , together with validation, including not restrict:
	 * <p>1. role code must be unique.
	 * 
	 * @param role
	 * @throws JServiceException
	 */
	void updateRole( Role role)throws BusinessException;
	
	/**
	 * delete the role, together with potential validations. 
	 * 
	 * @param role
	 * @throws JServiceException
	 */
	void deleteRole( Role role)throws BusinessException;
	
	/**
	 * get user by name & password 
	 * @param userName
	 * @param password
	 * @return
	 */
	public User getUserByNameAndPassword(String userName,String password);
	
	/**
	 * 
	 *  
	 * @param user
	 * @throws JServiceException
	 */
	public void saveUser( User user) throws BusinessException;
	
	
	/**
	 * 
	 * 
	 * @param user
	 * @throws JServiceException
	 */
	public void updateUser( User user) throws BusinessException;

	public void updateUser( User user,UserExtend userExtend) throws BusinessException;
	
	public void deleteUser( String userId);
	
	/**
	 * search user 
	 * 
	 * @param simplePageable
	 * @return
	 */
	public JPage<UserRecord> getUsersByPage(UserSearchCriteria userSearchCriteria, 
			JSimplePageable simplePageable) ;
	
	/**
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public UserRecord getUserById( String id);
	
	/**
	 * all users (not deleted) .
	 * @return
	 */
	public List<User> getUsers();
	
	/**
	 * register a user from views. its a component that wraps the logic related. 
	 * 
	 * @param user
	 * @throws JServiceException
	 */
	public void register(User user,UserExtend userExtend) throws BusinessException;
	
	/**
	 * @param userId
	 * 
	 * @param password
	 */
	public void resetPassword(String userId,String password);
	
	List<RoleGroup> getRoleGroupsByRoleId(String roleId);
	
	List<RoleGroup> getRoleGroupsByGroupId(String groupId);
	
	List<RoleRecord> getRolesByGroupId(String groupId);
	
	JPage<RoleRecord> getRolesByGroupIdByPage(String groupId,JSimplePageable simplePageable);
	
	/**
	 * tie the role to a group . 
	 * 
	 * @param  roleId
	 * @param groupId
	 */
	void bingRoleGroup(String roleId,String groupId) throws BusinessException;
	
	/**
	 * remove role from the group. 
	 * 
	 * @param  roleId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingRoleGroup(String roleId,String groupId) throws BusinessException;
	
	/**
	 * check if the role belongs to the group. 
	 * 
	 *  @param  roleId
	 * @param groupId
	 * @return
	 */
	boolean isBingRoleAndGroup(String roleId,String groupId);
	
	/**
	 * GET ROLE GROUP. 
	 * 
	 *  @param  roleId
	 * @param groupId
	 * @return
	 */
	RoleGroup getRoleGroupOnRoleIdAndGroupId(String roleId,String groupId);
	
	/**
	 * GET ROLE GROUP. 
	 * 
	 *  @param  roleId
	 * @param groupId
	 * @return
	 */
	long countOnRoleIdAndGroupId(String roleId,String groupId);
	
	
	List<UserRole> getUserRolesByUserId(String userId);
	
	JPage<UserRecord> getUsersByRoleIdByPage(String roleId,JSimplePageable simplePageable);
	
	List<UserRecord> getUsersByRoleId(String roleId);
	
	JPage<UserRecord> getUsersByGroupIdByPage(String groupId,JSimplePageable simplePageable);
	
	List<UserRecord> getUsersByGroupId(String groupId);
	
	/**
	 * grant the role to a user. 
	 * 
	 * @param userId
	 * @param roleId
	 */
	void bingUserRole(String userId,String roleId) throws BusinessException;
	
	/**
	 * remove role on the user. 
	 * 
	 * @param userId
	 * @param roleId
	 * @throws JServiceException
	 */
	void unbingUserRole(String userId,String roleId) throws BusinessException;
	
	/**
	 * check if the user has the role. 
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	boolean isBingUserAndRole(String userId,String roleId);
	
	/**
	 * GET USER ROLE. 
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	UserRole getUserRoleOnUserIdAndRoleId(String userId,String roleId);
	
	
	/**
	 * save user extension , that is always called by UserService. including some validations:
	 * <p>1. user id is not null
	 * <p>2. user name is not null
	 * <p>3. user nature name is unique in the system
	 *  
	 * @param userExtend
	 * @throws JServiceException
	 */
	public void saveUserExtend( UserExtend userExtend) throws BusinessException;
	
	
	/**
	 * update user extension,including some validations:
	 * <p>1. user id is not null
	 * <p>2. user name is not null
	 * <p>3. user nature name is unique in the system
	 * 
	 * @param userExtend
	 * @throws JServiceException
	 */
	public void updateUserExtend( UserExtend userExtend) throws BusinessException;
	
	/**
	 * get user extension by user id that is the primary key in the user model.
	 * 
	 * @param userId
	 * @return
	 * @see User
	 */
	public UserExtend getUserExtendByUserId( String userId);
	
	/**
	 * get user extension by nature name . the nature name is also unique in the system.
	 * 
	 * @param natureName  unique 
	 * @return
	 */
	public UserExtend getUserExtendByNatureName( String natureName);
	
	
	List<UserGroup> getUserGroupsByUserId(String userId);
	
	List<RoleRecord> getRolesByUserId(String userId);
	
	List<GroupRecord> getGroupsByUserId(String userId);
	
	List<GroupRecord> getUnbingGroupsByUserId(String userId);
	
	List<RoleRecord> getUnbingRolesByUserId(String userId);
	
	List<RoleRecord> getUnbingRolesByGroupId(String groupId);
	
	/**
	 * grant the group to a user. 
	 * 
	 * @param userId
	 * @param groupId
	 */
	void bingUserGroup(String userId,String groupId) throws BusinessException;
	
	/**
	 * remove group on the user. 
	 * 
	 * @param userId
	 * @param groupId
	 * @throws JServiceException
	 */
	void unbingUserGroup(String userId,String groupId) throws BusinessException;
	
	/**
	 * check if the user belongs to the group. 
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 */
	boolean isBingUserAndGroup(String userId,String groupId);
	
	/**
	 * GET USER GROUP. 
	 * 
	 * @param userId
	 * @param groupId
	 * @return
	 */
	UserGroup getUserGroupOnUserIdAndGroupId(String userId,String groupId) throws BusinessException;
	
}
