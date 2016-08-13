package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.sps.core.servicehub.SkipServiceNameCheck;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
@SkipServiceNameCheck
public class UserManagerServiceImpl
extends SpringServiceFactorySupport<UserManagerService>
implements UserManagerService {

	@Autowired
	private DefaultUserManagerServiceImpl defaultUserManagerServiceImpl;
	
	
	@Override
	public User getUserByName(ServiceContext context, String userName) {
		return defaultUserManagerServiceImpl.getUserByName(context, userName);
	}

	@Override
	public Group getGroupByGroupCode(ServiceContext serviceContext,
			String groupCode) {
		return defaultUserManagerServiceImpl.getGroupByGroupCode(serviceContext, groupCode);
	}

	@Override
	public Group getAdminGroup(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getAdminGroup(serviceContext);
	}

	@Override
	public Group getDefaultGroup(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getDefaultGroup(serviceContext);
	}

	@Override
	public JPage<Group> getGroupsByPage(ServiceContext serviceContext,
			GroupSearchCriteria groupSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getGroupsByPage(serviceContext, groupSearchCriteria, simplePageable);
	}

	@Override
	public List<Group> getAllGroups(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getAllGroups(serviceContext);
	}

	@Override
	public void saveGroup(ServiceContext context, Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveGroup(context, group);
	}

	@Override
	public boolean exists(ServiceContext context, Group group)
			throws BusinessException {
		return defaultUserManagerServiceImpl.exists(context, group);
	}

	@Override
	public void updateGroup(ServiceContext context, Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateGroup(context, group);
	}

	@Override
	public void deleteGroup(ServiceContext context, Group group)
			throws BusinessException {
		defaultUserManagerServiceImpl.deleteGroup(context, group);
	}

	@Override
	public Role getRoleByRoleCode(ServiceContext serviceContext, String roleCode) {
		return defaultUserManagerServiceImpl.getRoleByRoleCode(serviceContext, roleCode);
	}

	@Override
	public Role getRoleById(ServiceContext serviceContext, String id) {
		return defaultUserManagerServiceImpl.getRoleById(serviceContext, id);
	}

	@Override
	public Role getAdminRole(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getAdminRole(serviceContext);
	}

	@Override
	public Role getDefaultRole(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getDefaultRole(serviceContext);
	}

	@Override
	public JPage<ParamType> getAllRolesByPage(ServiceContext context,
			RoleSearchCriteria roleSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getAllRolesByPage(context, roleSearchCriteria, simplePageable);
	}

	@Override
	public List<Role> getAllRoles(ServiceContext serviceContext) {
		return defaultUserManagerServiceImpl.getAllRoles(serviceContext);
	}

	@Override
	public void saveRole(ServiceContext context, Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveRole(context, role);
	}

	@Override
	public boolean exists(ServiceContext context, Role role)
			throws BusinessException {
		return defaultUserManagerServiceImpl.exists(context, role);
	}

	@Override
	public void updateRole(ServiceContext context, Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateRole(context, role);
	}

	@Override
	public void deleteRole(ServiceContext context, Role role)
			throws BusinessException {
		defaultUserManagerServiceImpl.deleteRole(context, role);
	}

	@Override
	public User getUserByNameAndPassword(String userName, String password) {
		return defaultUserManagerServiceImpl.getUserByNameAndPassword(userName, password);
	}

	@Override
	public void saveUser(ServiceContext context, User user)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveUser(context, user);
	}

	@Override
	public void updateUser(ServiceContext context, User user)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateUser(context, user);
	}

	@Override
	public JPage<User> getUsersByPage(ServiceContext context,
			UserSearchCriteria userSearchCriteria,
			JSimplePageable simplePageable) {
		return defaultUserManagerServiceImpl.getUsersByPage(context, userSearchCriteria, simplePageable);
	}

	@Override
	public User getUserById(ServiceContext context, String id) {
		return defaultUserManagerServiceImpl.getUserById(context, id);
	}

	@Override
	public List<User> getUsers(ServiceContext context) {
		return defaultUserManagerServiceImpl.getUsers(context);
	}

	@Override
	public void register(ServiceContext context, User user,
			UserExtend userExtend) throws BusinessException {
		defaultUserManagerServiceImpl.register(context, user, userExtend);
	}

	@Override
	public void resetPassword(ServiceContext context, String userId,
			String password) {
		defaultUserManagerServiceImpl.resetPassword(context, userId, password);
	}

	@Override
	public List<RoleGroup> getRoleGroupsByRoleId(ServiceContext serviceContext,
			String roleId) {
		return defaultUserManagerServiceImpl.getRoleGroupsByRoleId(serviceContext, roleId);
	}

	@Override
	public List<RoleGroup> getRoleGroupsByGroupId(
			ServiceContext serviceContext, String groupId) {
		return defaultUserManagerServiceImpl.getRoleGroupsByGroupId(serviceContext, groupId);
	}

	@Override
	public void bingRoleGroup(ServiceContext serviceContext, String roleId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.bingRoleGroup(serviceContext, roleId, groupId);
	}

	@Override
	public void unbingRoleGroup(ServiceContext serviceContext, String roleId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingRoleGroup(serviceContext, roleId, groupId);
	}

	@Override
	public boolean isBingRoleAndGroup(ServiceContext serviceContext,
			String roleId, String groupId) {
		return defaultUserManagerServiceImpl.isBingRoleAndGroup(serviceContext, roleId, groupId);
	}

	@Override
	public RoleGroup getRoleGroupOnRoleIdAndGroupId(
			ServiceContext serviceContext, String roleId, String groupId) {
		return defaultUserManagerServiceImpl.getRoleGroupOnRoleIdAndGroupId(serviceContext, roleId, groupId);
	}

	@Override
	public long countOnRoleIdAndGroupId(ServiceContext serviceContext,
			String roleId, String groupId) {
		return defaultUserManagerServiceImpl.countOnRoleIdAndGroupId(serviceContext, roleId, groupId);
	}

	@Override
	public List<UserRole> getUserRolesByUserId(ServiceContext serviceContext,
			String userId) {
		return defaultUserManagerServiceImpl.getUserRolesByUserId(serviceContext, userId);
	}

	@Override
	public void bingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws BusinessException {
		defaultUserManagerServiceImpl.bingUserRole(serviceContext, userId, roleId);
	}

	@Override
	public void unbingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingUserRole(serviceContext, userId, roleId);
	}

	@Override
	public boolean isBingUserAndRole(ServiceContext serviceContext,
			String userId, String roleId) {
		return defaultUserManagerServiceImpl.isBingUserAndRole(serviceContext, userId, roleId);
	}

	@Override
	public UserRole getUserRoleOnUserIdAndRoleId(ServiceContext serviceContext,
			String userId, String roleId) {
		return defaultUserManagerServiceImpl.getUserRoleOnUserIdAndRoleId(serviceContext, userId, roleId);
	}

	@Override
	public void saveUserExtend(ServiceContext context, UserExtend userExtend)
			throws BusinessException {
		defaultUserManagerServiceImpl.saveUserExtend(context, userExtend);
	}

	@Override
	public void updateUserExtend(ServiceContext context, UserExtend userExtend)
			throws BusinessException {
		defaultUserManagerServiceImpl.updateUserExtend(context, userExtend);
	}

	@Override
	public UserExtend getUserExtendByUserId(ServiceContext context,
			String userId) {
		return defaultUserManagerServiceImpl.getUserExtendByUserId(context, userId);
	}

	@Override
	public UserExtend getUserExtendByNatureName(ServiceContext context,
			String natureName) {
		return defaultUserManagerServiceImpl.getUserExtendByNatureName(context, natureName);
	}

	@Override
	public List<UserGroup> getUserGroupsByUserId(ServiceContext serviceContext,
			String userId) {
		return defaultUserManagerServiceImpl.getUserGroupsByUserId(serviceContext, userId);
	}

	@Override
	public void bingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.bingUserGroup(serviceContext, userId, groupId);
	}

	@Override
	public void unbingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws BusinessException {
		defaultUserManagerServiceImpl.unbingUserGroup(serviceContext, userId, groupId);
	}

	@Override
	public boolean isBingUserAndGroup(ServiceContext serviceContext,
			String userId, String groupId) {
		return defaultUserManagerServiceImpl.isBingUserAndGroup(serviceContext, userId, groupId);
	}

	@Override
	public UserGroup getUserGroupOnUserIdAndGroupId(
			ServiceContext serviceContext, String userId, String groupId)
			throws BusinessException {
		return defaultUserManagerServiceImpl.getUserGroupOnUserIdAndGroupId(serviceContext, userId, groupId);
	}
	
}
