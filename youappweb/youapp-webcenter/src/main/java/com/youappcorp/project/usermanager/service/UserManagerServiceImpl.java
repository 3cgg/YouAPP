package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.sps.core.servicehub.SkipServiceNameCheck;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
@SkipServiceNameCheck
public class UserManagerServiceImpl
extends SpringServiceFactorySupport<UserManagerService>
implements UserManagerService {

	@Autowired
	private DefaultUserManagerServiceImpl defaultUserManagerServiceImpl;
	
	
	@Override
	public User getUserByName( String userName) {
		return defaultUserManagerServiceImpl.getUserByName( userName);
	}

	@Override
	public Group getGroupByGroupCode(
			String groupCode) {
		return defaultUserManagerServiceImpl.getGroupByGroupCode( groupCode);
	}

	@Override
	public Group getAdminGroup( ) {
		return defaultUserManagerServiceImpl.getAdminGroup();
	}

	@Override
	public Group getDefaultGroup( ) {
		return defaultUserManagerServiceImpl.getDefaultGroup();
	}

	@Override
	public JPage<Group> getGroupsByPage(
			GroupSearchCriteria groupSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getGroupsByPage( groupSearchCriteria, simplePageable);
	}

	@Override
	public List<Group> getAllGroups( ) {
		return defaultUserManagerServiceImpl.getAllGroups();
	}

	@Override
	public void saveGroup( Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveGroup( group);
	}

	@Override
	public boolean exists( Group group)
			throws BusinessException {
		return defaultUserManagerServiceImpl.exists( group);
	}

	@Override
	public void updateGroup( Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateGroup( group);
	}

	@Override
	public void deleteGroup( Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.deleteGroup( group);
	}

	@Override
	public Role getRoleByRoleCode( String roleCode) {
		return defaultUserManagerServiceImpl.getRoleByRoleCode( roleCode);
	}

	@Override
	public Role getRoleById( String id) {
		return defaultUserManagerServiceImpl.getRoleById( id);
	}

	@Override
	public Role getAdminRole( ) {
		return defaultUserManagerServiceImpl.getAdminRole();
	}

	@Override
	public Role getDefaultRole( ) {
		return defaultUserManagerServiceImpl.getDefaultRole();
	}

	@Override
	public JPage<Role> getAllRolesByPage(
			RoleSearchCriteria roleSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getAllRolesByPage( roleSearchCriteria, simplePageable);
	}

	@Override
	public List<Role> getAllRoles( ) {
		return defaultUserManagerServiceImpl.getAllRoles();
	}

	@Override
	public void saveRole( Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveRole( role);
	}

	@Override
	public boolean exists( Role role)
			throws BusinessException {
		return defaultUserManagerServiceImpl.exists( role);
	}

	@Override
	public void updateRole( Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateRole( role);
	}

	@Override
	public void deleteRole( Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.deleteRole( role);
	}

	@Override
	public User getUserByNameAndPassword(String userName, String password) {
		return defaultUserManagerServiceImpl.getUserByNameAndPassword(userName, password);
	}

	@Override
	public void saveUser( User user)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveUser( user);
	}

	@Override
	public void updateUser( User user)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateUser( user);
	}
	
	@Override
	public void updateUser( User user,
			UserExtend userExtend) throws BusinessException {
		defaultUserManagerServiceImpl.updateUser( user, userExtend);
	}

	@Override
	public JPage<UserRecord> getUsersByPage(
			UserSearchCriteria userSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUsersByPage( userSearchCriteria, simplePageable);
	}

	@Override
	public UserRecord getUserById( String id) {
		return defaultUserManagerServiceImpl.getUserById( id);
	}

	@Override
	public List<User> getUsers( ) {
		return defaultUserManagerServiceImpl.getUsers();
	}

	@Override
	public void register( User user,
			UserExtend userExtend) throws BusinessException {
		defaultUserManagerServiceImpl.register( user, userExtend);
	}

	@Override
	public void resetPassword( String userId,
			String password) {
		defaultUserManagerServiceImpl.resetPassword( userId, password);
	}

	@Override
	public List<RoleGroup> getRoleGroupsByRoleId(
			String roleId) {
		return defaultUserManagerServiceImpl.getRoleGroupsByRoleId( roleId);
	}

	@Override
	public List<RoleGroup> getRoleGroupsByGroupId(
			 String groupId) {
		return defaultUserManagerServiceImpl.getRoleGroupsByGroupId( groupId);
	}

	@Override
	public void bingRoleGroup( String roleId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.bingRoleGroup( roleId, groupId);
	}

	@Override
	public void unbingRoleGroup( String roleId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingRoleGroup( roleId, groupId);
	}

	@Override
	public boolean isBingRoleAndGroup(
			String roleId, String groupId) {
		return defaultUserManagerServiceImpl.isBingRoleAndGroup( roleId, groupId);
	}

	@Override
	public RoleGroup getRoleGroupOnRoleIdAndGroupId(
			 String roleId, String groupId) {
		return defaultUserManagerServiceImpl.getRoleGroupOnRoleIdAndGroupId( roleId, groupId);
	}

	@Override
	public long countOnRoleIdAndGroupId(
			String roleId, String groupId) {
		return defaultUserManagerServiceImpl.countOnRoleIdAndGroupId( roleId, groupId);
	}

	@Override
	public List<UserRole> getUserRolesByUserId(
			String userId) {
		return defaultUserManagerServiceImpl.getUserRolesByUserId( userId);
	}

	@Override
	public void bingUserRole( String userId,
			String roleId) throws BusinessException {
		defaultUserManagerServiceImpl.bingUserRole( userId, roleId);
	}

	@Override
	public void unbingUserRole( String userId,
			String roleId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingUserRole( userId, roleId);
	}

	@Override
	public boolean isBingUserAndRole(
			String userId, String roleId) {
		return defaultUserManagerServiceImpl.isBingUserAndRole( userId, roleId);
	}

	@Override
	public UserRole getUserRoleOnUserIdAndRoleId(
			String userId, String roleId) {
		return defaultUserManagerServiceImpl.getUserRoleOnUserIdAndRoleId( userId, roleId);
	}

	@Override
	public void saveUserExtend( UserExtend userExtend)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveUserExtend( userExtend);
	}

	@Override
	public void updateUserExtend( UserExtend userExtend)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateUserExtend( userExtend);
	}

	@Override
	public UserExtend getUserExtendByUserId(
			String userId) {
		return defaultUserManagerServiceImpl.getUserExtendByUserId( userId);
	}

	@Override
	public UserExtend getUserExtendByNatureName(
			String natureName) {
		return defaultUserManagerServiceImpl.getUserExtendByNatureName( natureName);
	}

	@Override
	public List<UserGroup> getUserGroupsByUserId(
			String userId) {
		return defaultUserManagerServiceImpl.getUserGroupsByUserId( userId);
	}

	@Override
	public void bingUserGroup( String userId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.bingUserGroup( userId, groupId);
	}

	@Override
	public void unbingUserGroup( String userId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingUserGroup( userId, groupId);
	}

	@Override
	public boolean isBingUserAndGroup(
			String userId, String groupId) {
		return defaultUserManagerServiceImpl.isBingUserAndGroup( userId, groupId);
	}

	@Override
	public UserGroup getUserGroupOnUserIdAndGroupId(
			 String userId, String groupId)
			throws BusinessException {
		return defaultUserManagerServiceImpl.getUserGroupOnUserIdAndGroupId( userId, groupId);
	}

	@Override
	public UserDetail getUserDetailByName(
			String userName) {
		return defaultUserManagerServiceImpl.getUserDetailByName( userName);
	}

	@Override
	public UserDetail getUserDetailById( String id) {
		return defaultUserManagerServiceImpl.getUserDetailById( id);
	}

	@Override
	public Group getGroupById( String id) {
		return defaultUserManagerServiceImpl.getGroupById( id);
	}

	@Override
	public JPage<UserRecord> getUsersByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUsersByRoleIdByPage( roleId, simplePageable);
	}

	@Override
	public List<UserRecord> getUsersByRoleId(
			 String roleId) {
		return defaultUserManagerServiceImpl.getUsersByRoleId( roleId);
	}

	@Override
	public JPage<UserRecord> getUsersByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUsersByGroupIdByPage( groupId, simplePageable);
	}

	@Override
	public List<UserRecord> getUsersByGroupId(
			 String groupId) {
		return defaultUserManagerServiceImpl.getUsersByGroupId( groupId);
	}

	@Override
	public List<RoleRecord> getRolesByGroupId(
			 String groupId) {
		return defaultUserManagerServiceImpl.getRolesByGroupId( groupId);
	}

	@Override
	public JPage<RoleRecord> getRolesByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getRolesByGroupIdByPage( groupId, simplePageable);
	}

	@Override
	public List<GroupRecord> getGroupsByRoleId(
			String roleId) {
		return defaultUserManagerServiceImpl.getGroupsByRoleId( roleId);
	}

	@Override
	public JPage<GroupRecord> getGroupsByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getGroupsByRoleIdByPage( roleId, simplePageable);
	}

	@Override
	public List<RoleRecord> getRolesByUserId(
			String userId) {
		return defaultUserManagerServiceImpl.getRolesByUserId( userId);
	}

	@Override
	public List<GroupRecord> getGroupsByUserId(
			String userId) {
		return defaultUserManagerServiceImpl.getGroupsByUserId( userId);
	}

	@Override
	public List<GroupRecord> getUnbingGroupsByRoleId(
			 String roleId) {
		return defaultUserManagerServiceImpl.getUnbingGroupsByRoleId( roleId);
	}

	@Override
	public JPage<GroupRecord> getUnbingGroupsByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUnbingGroupsByRoleIdByPage( roleId, simplePageable);
	}

	@Override
	public List<GroupRecord> getUnbingGroupsByUserId(
			 String userId) {
		return defaultUserManagerServiceImpl.getUnbingGroupsByUserId( userId);
	}

	@Override
	public JPage<UserRecord> getUnbingUsersByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUnbingUsersByRoleIdByPage( roleId, simplePageable);
	}

	@Override
	public JPage<UserRecord> getUnbingUsersByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUnbingUsersByGroupIdByPage( groupId, simplePageable);
	}

	@Override
	public List<RoleRecord> getUnbingRolesByUserId(
			 String userId) {
		return defaultUserManagerServiceImpl.getUnbingRolesByUserId( userId);
	}

	@Override
	public List<RoleRecord> getUnbingRolesByGroupId(
			 String groupId) {
		return defaultUserManagerServiceImpl.getUnbingRolesByGroupId( groupId);
	}

	@Override
	public void deleteUser( String userId) {
		defaultUserManagerServiceImpl.deleteUser( userId);
	}

	@Override
	public boolean isAdminRole( String roleId) {
		return defaultUserManagerServiceImpl.isAdminRole( roleId);
	}

	@Override
	public boolean isDefaultRole( String roleId) {
		return defaultUserManagerServiceImpl.isDefaultRole( roleId);
	}
	
	@Override
	public boolean isAdminRoleCode( String code) {
		return defaultUserManagerServiceImpl.isAdminRoleCode( code);
	}
	
	@Override
	public boolean isDefaultRoleCode( String code) {
		return defaultUserManagerServiceImpl.isDefaultRoleCode( code);
	}

	@Override
	public boolean isDefaultGroup( String groupId) {
		return defaultUserManagerServiceImpl.isDefaultGroup( groupId);
	}

	@Override
	public boolean isDefaultGroupCode( String code) {
		return defaultUserManagerServiceImpl.isDefaultGroupCode( code);
	}

	@Override
	public boolean isAdminGroup( String groupId) {
		return defaultUserManagerServiceImpl.isAdminGroup( groupId);
	}

	@Override
	public boolean isAdminGroupCode( String code) {
		return defaultUserManagerServiceImpl.isAdminGroupCode( code);
	}
	
}
