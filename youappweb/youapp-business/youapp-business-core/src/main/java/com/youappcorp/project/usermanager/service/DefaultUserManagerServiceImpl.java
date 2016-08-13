package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.sps.support.security.subhub.DESedeCipherService;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
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

@Service(value="defaultUserManagerServiceImpl.transation.jpa")
public class DefaultUserManagerServiceImpl extends ServiceSupport
implements UserManagerService {
	
	@Autowired
	private InternalGroupServiceImpl internalGroupServiceImpl;
	
	@Override
	public Group getGroupByGroupCode(ServiceContext serviceContext, String groupCode) {
		JAssert.isNotEmpty(groupCode);
		return internalGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("groupCode", groupCode).ready().model();
	}
	
	@Override
	public Group getAdminGroup(ServiceContext serviceContext) {
		return getGroupByGroupCode(serviceContext, ADMIN_CODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Group getDefaultGroup(ServiceContext serviceContext) {
		return getGroupByGroupCode(serviceContext, DEFAULT_CODE);
	}
	
	@Override
	public JPage<Group> getGroupsByPage(ServiceContext serviceContext,
			GroupSearchCriteria groupSearchCriteria, JSimplePageable simplePageable){
		return internalGroupServiceImpl.singleEntityQuery().conditionDefault()
				.likes("roleCode", groupSearchCriteria.getRoleCode())
				.likes("roleName", groupSearchCriteria.getRoleName())
				.likes("description", groupSearchCriteria.getDescription())
				.ready().modelPage(simplePageable);
	}
	
	@Override
	public List<Group> getAllGroups(ServiceContext serviceContext) {
		return internalGroupServiceImpl.singleEntityQuery()
				.conditionDefault().ready().models();
	}
	
	
	@Override
	public void saveGroup(ServiceContext context, Group group)
			throws BusinessException {
		try{
			validateGroupCode(group);
			
			if(exists(context, group)){
				throw new BusinessException("group code ["+group.getGroupCode()+"] already has exist.");
			}
			internalGroupServiceImpl.saveOnly(context, group);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean exists(ServiceContext context, Group group)
			throws BusinessException {
		boolean exists=false;
		try{
			if(group==null){
				throw new IllegalArgumentException("role argument is null");
			}
			
			Group dbGroup=getGroupByGroupCode(context, group.getGroupCode());
			// new created.
			if(JStringUtils.isNullOrEmpty(group.getId())){
				exists= dbGroup!=null;
			}
			else{
				// updated status.
				if(dbGroup!=null){
					// if it's self
					exists=!group.getId().equals(dbGroup.getId());
				}
				else{
					exists=false;
				}
			}
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return exists;
	}
	
	private void validateGroupCode(Group group) throws BusinessException{
		
		String code=group.getGroupCode();
		
		if(JStringUtils.isNullOrEmpty(code)){
			throw new IllegalArgumentException("group code  is null"); 
		}
		code=code.trim();
		
		if(ADMIN_CODE.equalsIgnoreCase(code)){
			throw new BusinessException("group code ["+ADMIN_CODE+"] is initialized by system.please change...");
		}
		
		if(DEFAULT_CODE.equalsIgnoreCase(code)){
			throw new BusinessException("group code ["+DEFAULT_CODE+"] is initialized by system.please change...");
		}
	}
	
	@Override
	public void updateGroup(ServiceContext context, Group group)
			throws BusinessException {
		try{
			validateGroupCode(group);
			
			if(JStringUtils.isNullOrEmpty(group.getId())){
				throw new IllegalArgumentException("the primary property id of group is null.");
			}
			
			if(exists(context, group)){
				throw new BusinessException("group code ["+group.getGroupCode()+"] already has exist.");
			}
			internalGroupServiceImpl.updateOnly(context, group);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void deleteGroup(ServiceContext context, Group group)
			throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(group.getId())){
				throw new IllegalArgumentException("the primary property id of group is null.");
			}
			
			if(JStringUtils.isNullOrEmpty(group.getGroupCode())){
				group.setGroupCode(internalGroupServiceImpl.getById(context, group.getId()).getGroupCode());
			}
			
			validateGroupCode(group);
			internalGroupServiceImpl.delete(context, group.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Autowired
	private InternalRoleServiceImpl internalRoleServiceImpl;
	
	@Override
	public Role getRoleByRoleCode(ServiceContext serviceContext, String roleCode) {
		return internalRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("roleCode", roleCode).ready().model();
	}
	
	@Override
	public Role getAdminRole(ServiceContext serviceContext) {
		return getRoleByRoleCode(serviceContext, ADMIN_CODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getDefaultRole(ServiceContext serviceContext) {
		return getRoleByRoleCode(serviceContext, DEFAULT_CODE);
	}
	
	@Override
	public JPage<ParamType> getAllRolesByPage(ServiceContext context,
			RoleSearchCriteria roleSearchCriteria,
			JSimplePageable simplePageable) {
		return null;
	}
	
	@Override
	public List<Role> getAllRoles(ServiceContext serviceContext) {
		return internalRoleServiceImpl.singleEntityQuery().conditionDefault().ready().models();
	}
	
	
	private void validateRoleCode(Role role) throws BusinessException{
		
		String code=role.getRoleCode();
		
		if(JStringUtils.isNullOrEmpty( code)){
			throw new IllegalArgumentException("role code  is null"); 
		}
		
		code=code.trim();
		
		if(ADMIN_CODE.equalsIgnoreCase( code)){
			throw new BusinessException("role code ["+ADMIN_CODE+"] is initialized by system.please change...");
		}
		
		if(DEFAULT_CODE.equalsIgnoreCase( code)){
			throw new BusinessException("role code ["+DEFAULT_CODE+"] is initialized by system.please change...");
		}
	}
	
	@Override
	public void saveRole(ServiceContext context, Role role)
			throws BusinessException {
		
		try{

			validateRoleCode(role);
			if(exists(context, role)){
				throw new BusinessException("role code ["+role.getRoleCode()+"] already has exist.");
			}
			internalRoleServiceImpl.saveOnly(context, role);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean exists(ServiceContext context, Role role)
			throws BusinessException {
		if(role==null){
			throw new IllegalArgumentException("role argument is null");
		}
		boolean exists=false;
		Role dbRole=getRoleByRoleCode(context, role.getRoleCode());
		// new created.
		if(JStringUtils.isNullOrEmpty(role.getId())){
			exists= dbRole!=null;
		}
		else{
			// updated status.
			if(dbRole!=null){
				// if it's self
				exists=!role.getId().equals(dbRole.getId());
			}
			else{
				exists=false;
			}
		}
		return exists;
	}
	
	@Override
	public void updateRole(ServiceContext context, Role role)
			throws BusinessException {
		try{
			validateRoleCode(role);
			
			if(JStringUtils.isNullOrEmpty(role.getId())){
				throw new IllegalArgumentException("the primary property id of role is null.");
			}
			
			if(exists(context, role)){
				throw new BusinessException("role code ["+role.getRoleCode()+"] already has exist.");
			}
			internalRoleServiceImpl.updateOnly(context, role);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void deleteRole(ServiceContext context, Role role)
			throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(role.getId())){
				throw new IllegalArgumentException("the primary property id of role is null.");
			}
			
			if(JStringUtils.isNullOrEmpty(role.getRoleCode())){
				role.setRoleCode(internalRoleServiceImpl.getById(context, role.getId()).getRoleCode());
			}
			validateRoleCode(role);
			internalRoleServiceImpl.delete(context, role.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public Role getRoleById(ServiceContext serviceContext, String id) {
		return internalRoleServiceImpl.getById(serviceContext, id);
	}
	
	
	@Autowired
	private InternalUserServiceImpl internalUserServiceImpl;
	
	private DESedeCipherService deSedeCipherService=
			JServiceHubDelegate.get().getService(this, DESedeCipherService.class);
	
	@Override
	public User getUserByNameAndPassword(String userName, String password) {
		return internalUserServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userName", userName)
				.equals("password", password).ready().model();
	}
	
		
	@Override
	public void saveUser(ServiceContext context, User user) throws BusinessException {
		try{
			internalUserServiceImpl.saveOnly(context, user);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void updateUser(ServiceContext context, User user)
			throws BusinessException {
		try{
			internalUserServiceImpl.updateOnly(context, user);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
		
	
	@Override
	public User getUserByName(ServiceContext context, String userName) {
		return internalUserServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userName", userName).ready().model();
	}
	
	
	@Override
	public JPage<User> getUsersByPage(ServiceContext context, UserSearchCriteria userSearchCriteria,
			JSimplePageable simplePageable) {
		return internalUserServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userName", userSearchCriteria.getUserName()	).ready().modelPage(simplePageable);
	}
	
	@Override
	public User getUserById(ServiceContext context, String id) {
		return internalUserServiceImpl.getById(context, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsers(ServiceContext context) {
		return internalUserServiceImpl.singleEntityQuery().conditionDefault().ready().models();
	}

	
	@Override
	public void register(ServiceContext context,User user,UserExtend userExtend) throws BusinessException {
		try{
			
			if(JStringUtils.isNullOrEmpty(user.getUserName())){
				throw new BusinessException("用户名不能为空");
			}
			
			if(JStringUtils.isNullOrEmpty(user.getPassword())){
				throw new BusinessException("密码不能为空");
			}		
			
			User dbUser=getUserByName(context, user.getUserName().trim());
			if(dbUser!=null){
				throw new BusinessException("用户已经存在");
			}
			
			String passwrod=user.getPassword().trim();
			String encriptPassword=deSedeCipherService.encrypt(passwrod);
			user.setPassword(encriptPassword);
			user.setUserName(user.getUserName().trim());
			saveUser(context, user);  // with encrypted password
			
			//save user extend
			userExtend.setUserId(user.getId());
			saveUserExtend(context, userExtend);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	
	@Override
	public void resetPassword(ServiceContext context, String userId,String password) {
		try {
			User dbUser=getUserById(context, userId);
			if(dbUser==null){
				throw new BusinessException("用户不存在");
			}
			
			String passwrod=password.trim();
			String encriptPassword=deSedeCipherService.encrypt(passwrod);
			
			dbUser.setPassword(encriptPassword);
			internalUserServiceImpl.saveOnly(context, dbUser);
		} catch (Exception e) {
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	
	
	
	
	
	@Autowired
	private InternalRoleGroupServiceImpl internalRoleGroupServiceImpl;
	
	@Override
	public void bingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) throws BusinessException {
		
		try{
			if(isBingRoleAndGroup(serviceContext, roleId, groupId)){
				throw new BusinessException("the role had already belong to the group.");
			}
			
			RoleGroup roleGroup=new RoleGroup();
			roleGroup.setRoleId(roleId);
			roleGroup.setGroupId(groupId);
			roleGroup.setId(JUniqueUtils.unique());
			internalRoleGroupServiceImpl.saveOnly(serviceContext, roleGroup);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void unbingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) 
			throws BusinessException {
		
		try{

			RoleGroup userGroup=getRoleGroupOnRoleIdAndGroupId(serviceContext, roleId, groupId);
			if(userGroup==null){
				throw new BusinessException("the user doesnot belong to the group.");
			}
			internalRoleGroupServiceImpl.delete(serviceContext, userGroup.getId());
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public RoleGroup getRoleGroupOnRoleIdAndGroupId(
			ServiceContext serviceContext, String roleId, String groupId) {
		return internalRoleGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("roleId", roleId)
				.equals("groupId", groupId)
				.ready().model();
	}
	
	@Override
	public long countOnRoleIdAndGroupId(ServiceContext serviceContext,
			String roleId, String groupId) {
		return internalRoleGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("roleId", roleId)
				.equals("groupId", groupId)
				.ready().count();
	}
	
	@Override
	public boolean isBingRoleAndGroup(ServiceContext serviceContext,String roleId,String groupId) {
		long count=countOnRoleIdAndGroupId(serviceContext, roleId, groupId);
		return count>0;
	}

	@Override
	public List<RoleGroup> getRoleGroupsByRoleId(ServiceContext serviceContext,
			String roleId) {
		return internalRoleGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("roleId", roleId)
				.ready().models();
	}

	@Override
	public List<RoleGroup> getRoleGroupsByGroupId(
			ServiceContext serviceContext, String groupId) {
		return internalRoleGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("groupId", groupId)
				.ready().models();
	}
	
	
	@Autowired
	private InternalUserRoleServiceImpl internalUserRoleServiceImpl;
	
	@Override
	public List<UserRole> getUserRolesByUserId(ServiceContext serviceContext,
			String userId) {
		return internalUserRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().models();
	}
	
	@Override
	public void bingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws BusinessException {
		
		try{
			if(isBingUserAndRole(serviceContext, userId, roleId)){
				throw new BusinessException("the user had already the role.");
			}
			
			UserRole userRole=new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			userRole.setId(JUniqueUtils.unique());
			internalUserRoleServiceImpl.saveOnly(serviceContext, userRole);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
		
	}
	
	@Override
	public void unbingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws BusinessException {
		try{
			UserRole userRole=getUserRoleOnUserIdAndRoleId(serviceContext, userId, roleId);
			if(userRole==null){
				throw new BusinessException("the user doesnot have the role.");
			}
			internalUserRoleServiceImpl.delete(serviceContext, userRole.getId());
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public UserRole getUserRoleOnUserIdAndRoleId(ServiceContext serviceContext,
			String userId, String roleId) {
		return internalUserRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId)
				.equals("roleId", roleId)
				.ready().model();
	}
	
	@Override
	public boolean isBingUserAndRole(ServiceContext serviceContext, String userId,
			String roleId) {
		long count=internalUserRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId)
				.equals("roleId", roleId)
				.ready().count();
		return count>0;
	}
	
	
	
	
	@Autowired
	private InternalUserExtendServiceImpl internalUserExtendServiceImpl;
	
	
	@Override
	public void saveUserExtend(ServiceContext context, UserExtend userExtend)
			throws BusinessException {
		
		try{
			

			if(JStringUtils.isNullOrEmpty(userExtend.getUserId())){
				throw new BusinessException("user id is missing , when inserting user extenstion.");
			}
			
			if(JStringUtils.isNullOrEmpty(userExtend.getUserName())){
				throw new BusinessException("user name is missing , when inserting user extenstion.");
			}
			
			String natureName=userExtend.getNatureName();
			if(JStringUtils.isNotNullOrEmpty(natureName)){
				UserExtend dbUserExtend=getUserExtendByNatureName(context, userExtend.getNatureName());
				if(dbUserExtend!=null){
					throw new BusinessException("nature name already exists, please change others.");
				}
			}
			
			internalUserExtendServiceImpl.saveOnly(context, userExtend);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateUserExtend(ServiceContext context, UserExtend userExtend)
			throws BusinessException {
		
		try{
			if(JStringUtils.isNullOrEmpty(userExtend.getUserId())){
				throw new BusinessException("user id is missing , when inserting user extenstion.");
			}
			
			if(JStringUtils.isNullOrEmpty(userExtend.getUserName())){
				throw new BusinessException("user name is missing , when inserting user extenstion.");
			}
			
			String natureName=userExtend.getNatureName();
			if(JStringUtils.isNotNullOrEmpty(natureName)){
				UserExtend dbUserExtend=getUserExtendByNatureName(context, userExtend.getNatureName());
				if(dbUserExtend!=null&&!userExtend.getId().equals(dbUserExtend.getId())){
					throw new BusinessException("nature name already exists, please change others.");
				}
			}
			
			internalUserExtendServiceImpl.updateOnly(context, userExtend);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
		
	}

	@Override
	public UserExtend getUserExtendByUserId(ServiceContext context,
			String userId) {
		return internalUserExtendServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().model();
	}

	@Override
	public UserExtend getUserExtendByNatureName(ServiceContext context,
			String natureName) {
		return internalUserExtendServiceImpl.singleEntityQuery().conditionDefault()
				.equals("natureName", natureName).ready().model();
	}

	
	@Autowired
	private InternalUserGroupServiceImpl internalUserGroupServiceImpl;

	@Override
	public List<UserGroup> getUserGroupsByUserId(ServiceContext serviceContext,
			String userId) {
		return internalUserGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().models();
	}
	
	@Override
	public void bingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws BusinessException {
		
		try{

			if(isBingUserAndGroup(serviceContext, userId, groupId)){
				throw new BusinessException("the user had already belong to the group.");
			}
			
			UserGroup userGroup=new UserGroup();
			userGroup.setUserId(userId);
			userGroup.setGroupId(groupId);
			userGroup.setId(JUniqueUtils.unique());
			internalUserGroupServiceImpl.saveOnly(serviceContext, userGroup);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void unbingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws BusinessException {
		try{
			UserGroup userGroup=getUserGroupOnUserIdAndGroupId(serviceContext, userId, groupId);
			if(userGroup==null){
				throw new BusinessException("the user doesnot belong to the group.");
			}
			internalUserGroupServiceImpl.delete(serviceContext, userGroup.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public UserGroup getUserGroupOnUserIdAndGroupId(ServiceContext serviceContext,
			String userId, String groupId) {
		return internalUserGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId)
				.equals("groupId", groupId)
				.ready().model();
	}
	
	@Override
	public boolean isBingUserAndGroup(ServiceContext serviceContext, String userId,
			String groupId) {
		long count=internalUserGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId)
				.equals("groupId", groupId)
				.ready().count();
		return count>0;
	}
	
	
	
	
	
	
	
	
}
