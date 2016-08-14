package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.jpa.springjpa.query.JQuery;
import j.jave.platform.sps.support.security.subhub.DESedeCipherService;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
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
				.likes("groupCode", groupSearchCriteria.getGroupCode())
				.likes("groupName", groupSearchCriteria.getGroupName())
				.likes("description", groupSearchCriteria.getDescription())
				.ready().modelPage(simplePageable);
	}
	
	@Override
	public List<Group> getAllGroups(ServiceContext serviceContext) {
		return internalGroupServiceImpl.singleEntityQuery()
				.conditionDefault().ready().models();
	}
	
	
	@Override
	public void saveGroup(ServiceContext serviceContext, Group group)
			throws BusinessException {
		try{
			validateGroupCode(group);
			
			if(exists(serviceContext, group)){
				throw new BusinessException("group code ["+group.getGroupCode()+"] already has exist.");
			}
			internalGroupServiceImpl.saveOnly(serviceContext, group);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean exists(ServiceContext serviceContext, Group group)
			throws BusinessException {
		boolean exists=false;
		try{
			if(group==null){
				throw new IllegalArgumentException("role argument is null");
			}
			
			Group dbGroup=getGroupByGroupCode(serviceContext, group.getGroupCode());
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
	public void updateGroup(ServiceContext serviceContext, Group group)
			throws BusinessException {
		try{
			validateGroupCode(group);
			
			if(JStringUtils.isNullOrEmpty(group.getId())){
				throw new IllegalArgumentException("the primary property id of group is null.");
			}
			
			if(exists(serviceContext, group)){
				throw new BusinessException("group code ["+group.getGroupCode()+"] already has exist.");
			}
			
			Group dbGroup=getGroupById(serviceContext, group.getId());
			
			if(ADMIN_CODE.equalsIgnoreCase(dbGroup.getGroupCode())){
				throw new BusinessException("group code ["+ADMIN_CODE+"] is initialized by system.please change...");
			}
			
			if(DEFAULT_CODE.equalsIgnoreCase(dbGroup.getGroupCode())){
				throw new BusinessException("group code ["+DEFAULT_CODE+"] is initialized by system.please change...");
			}
			
			dbGroup.setGroupCode(group.getGroupCode());
			dbGroup.setGroupName(group.getGroupName());
			dbGroup.setDescription(group.getDescription());
			internalGroupServiceImpl.updateOnly(serviceContext, dbGroup);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void deleteGroup(ServiceContext serviceContext, Group group)
			throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(group.getId())){
				throw new IllegalArgumentException("the primary property id of group is null.");
			}
			
			if(JStringUtils.isNullOrEmpty(group.getGroupCode())){
				group.setGroupCode(internalGroupServiceImpl.getById(serviceContext, group.getId()).getGroupCode());
			}
			
			validateGroupCode(group);
			internalGroupServiceImpl.delete(serviceContext, group.getId());
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
	public JPage<Role> getAllRolesByPage(ServiceContext serviceContext,
			RoleSearchCriteria roleSearchCriteria,
			JSimplePageable simplePageable) {
		return internalRoleServiceImpl.singleEntityQuery().conditionDefault()
				.likes("roleCode", roleSearchCriteria.getRoleCode())
				.likes("roleName", roleSearchCriteria.getRoleName())
				.likes("description", roleSearchCriteria.getDescription())
				.ready().modelPage(simplePageable);
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
	public void saveRole(ServiceContext serviceContext, Role role)
			throws BusinessException {
		
		try{

			validateRoleCode(role);
			if(exists(serviceContext, role)){
				throw new BusinessException("role code ["+role.getRoleCode()+"] already has exist.");
			}
			internalRoleServiceImpl.saveOnly(serviceContext, role);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean exists(ServiceContext serviceContext, Role role)
			throws BusinessException {
		if(role==null){
			throw new IllegalArgumentException("role argument is null");
		}
		boolean exists=false;
		Role dbRole=getRoleByRoleCode(serviceContext, role.getRoleCode());
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
	public void updateRole(ServiceContext serviceContext, Role role)
			throws BusinessException {
		try{
			validateRoleCode(role);
			
			if(JStringUtils.isNullOrEmpty(role.getId())){
				throw new IllegalArgumentException("the primary property id of role is null.");
			}
			
			if(exists(serviceContext, role)){
				throw new BusinessException("role code ["+role.getRoleCode()+"] already has exist.");
			}
			
			Role dbRole=getRoleById(serviceContext, role.getId());
			
			if(ADMIN_CODE.equalsIgnoreCase(dbRole.getRoleCode())){
				throw new BusinessException("group code ["+ADMIN_CODE+"] is initialized by system.please change...");
			}
			
			if(DEFAULT_CODE.equalsIgnoreCase(dbRole.getRoleCode())){
				throw new BusinessException("group code ["+DEFAULT_CODE+"] is initialized by system.please change...");
			}
			
			dbRole.setRoleCode(role.getRoleCode());
			dbRole.setRoleName(role.getRoleName());
			dbRole.setDescription(role.getDescription());
			
			internalRoleServiceImpl.updateOnly(serviceContext, dbRole);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void deleteRole(ServiceContext serviceContext, Role role)
			throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(role.getId())){
				throw new IllegalArgumentException("the primary property id of role is null.");
			}
			
			if(JStringUtils.isNullOrEmpty(role.getRoleCode())){
				role.setRoleCode(internalRoleServiceImpl.getById(serviceContext, role.getId()).getRoleCode());
			}
			validateRoleCode(role);
			internalRoleServiceImpl.delete(serviceContext, role.getId());
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
	public void saveUser(ServiceContext serviceContext, User user) throws BusinessException {
		try{
			internalUserServiceImpl.saveOnly(serviceContext, user);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void updateUser(ServiceContext serviceContext, User user)
			throws BusinessException {
		try{
			internalUserServiceImpl.updateOnly(serviceContext, user);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void updateUser(ServiceContext serviceContext, User user,
			UserExtend userExtend) throws BusinessException {
//		updateUser(serviceContext, user);
		UserExtend dbUserExtend=getUserExtendByUserId(serviceContext, user.getId());
		dbUserExtend.setNatureName(userExtend.getNatureName());
		updateUserExtend(serviceContext, dbUserExtend);
	}
	
	@Override
	public void deleteUser(ServiceContext serviceContext, String userId) {
		internalUserServiceImpl.delete(serviceContext, userId);
	}
	
	@Override
	public User getUserByName(ServiceContext serviceContext, String userName) {
		return internalUserServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userName", userName).ready().model();
	}
	
	
	@Override
	public JPage<UserRecord> getUsersByPage(ServiceContext serviceContext, UserSearchCriteria userSearchCriteria,
			JSimplePageable simplePageable) {
		
		String jpql="select a.id as id"
				+ ",  a.userName as userName"
				+ " , a.status as status"
				+ " , a.registerTime as registerTime"
				+ ", d.natureName as natureName "
				+ " from User a "
				+ " left join UserExtend d on a.id=d.userId"
				+ " where a.deleted='N' ";
		Map<String, Object> params=new HashMap<String, Object>();
		String status=userSearchCriteria.getStatus();
		if(JStringUtils.isNotNullOrEmpty(status)){
			jpql=jpql+" and a.status= :status";
			params.put("status", status);
		}
		
		String userName=userSearchCriteria.getUserName();
		if(JStringUtils.isNotNullOrEmpty(userName)){
			jpql=jpql+" and a.userName like :userName";
			params.put("userName", "%"+userName+"%");
		}
		
		String natureName=userSearchCriteria.getNatureName();
		if(JStringUtils.isNotNullOrEmpty(natureName)){
			jpql=jpql+" and d.natureName like :natureName";
			params.put("natureName", "%"+natureName+"%");
		}
		
		String registerTimeStart=userSearchCriteria.getRegisterTimeStart();
		if(JStringUtils.isNotNullOrEmpty(registerTimeStart)){
			jpql=jpql+" and a.registerTime > :registerTimeStart";
			params.put("registerTimeStart", registerTimeStart);
		}
		
		String registerTimeEnd=userSearchCriteria.getRegisterTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(registerTimeStart)){
			jpql=jpql+" and a.registerTime < :registerTimeEnd";
			params.put("registerTimeEnd", registerTimeEnd);
		}
		
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params)
				.modelPage(simplePageable,UserRecord.class);
	}
	
	@Override
	public UserRecord getUserById(ServiceContext serviceContext, String id) {
		String jpql="select a.id as id"
				+ ",  a.userName as userName"
				+ " , a.status as status"
				+ " , a.registerTime as registerTime"
				+ ", d.natureName as natureName "
				+ " from User a "
				+ " left join UserExtend d on a.id=d.userId"
				+ " where a.deleted='N' and a.id= :userId";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", id);
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params)
				.model(UserRecord.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsers(ServiceContext serviceContext) {
		return internalUserServiceImpl.singleEntityQuery().conditionDefault().ready().models();
	}

	
	@Override
	public void register(ServiceContext serviceContext,User user,UserExtend userExtend) throws BusinessException {
		try{
			
			if(JStringUtils.isNullOrEmpty(user.getUserName())){
				throw new BusinessException("用户名不能为空");
			}
			
			if(JStringUtils.isNullOrEmpty(user.getPassword())){
				throw new BusinessException("密码不能为空");
			}		
			
			User dbUser=getUserByName(serviceContext, user.getUserName().trim());
			if(dbUser!=null){
				throw new BusinessException("用户已经存在");
			}
			
			String passwrod=user.getPassword().trim();
			String encriptPassword=deSedeCipherService.encrypt(passwrod);
			user.setPassword(encriptPassword);
			user.setUserName(user.getUserName().trim());
			user.setStatus("ACTIVE");
			user.setRegisterTime(new Timestamp(new Date().getTime()));
			saveUser(serviceContext, user);  // with encrypted password
			
			//save user extend
			userExtend.setUserId(user.getId());
			saveUserExtend(serviceContext, userExtend);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	
	@Override
	public void resetPassword(ServiceContext serviceContext, String userId,String password) {
		try {
			User dbUser=internalUserServiceImpl.getById(serviceContext, userId);
			if(dbUser==null){
				throw new BusinessException("用户不存在");
			}
			
			String passwrod=password.trim();
			String encriptPassword=deSedeCipherService.encrypt(passwrod);
			
			dbUser.setPassword(encriptPassword);
			internalUserServiceImpl.saveOnly(serviceContext, dbUser);
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
	public void saveUserExtend(ServiceContext serviceContext, UserExtend userExtend)
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
				UserExtend dbUserExtend=getUserExtendByNatureName(serviceContext, userExtend.getNatureName());
				if(dbUserExtend!=null){
					throw new BusinessException("nature name already exists, please change others.");
				}
			}
			
			internalUserExtendServiceImpl.saveOnly(serviceContext, userExtend);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateUserExtend(ServiceContext serviceContext, UserExtend userExtend)
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
				UserExtend dbUserExtend=getUserExtendByNatureName(serviceContext, userExtend.getNatureName());
				if(dbUserExtend!=null&&!userExtend.getId().equals(dbUserExtend.getId())){
					throw new BusinessException("nature name already exists, please change others.");
				}
			}
			
			internalUserExtendServiceImpl.updateOnly(serviceContext, userExtend);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
		
	}

	@Override
	public UserExtend getUserExtendByUserId(ServiceContext serviceContext,
			String userId) {
		return internalUserExtendServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().model();
	}

	@Override
	public UserExtend getUserExtendByNatureName(ServiceContext serviceContext,
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
	
	@Override
	public Group getGroupById(ServiceContext serviceContext, String id) {
		return internalGroupServiceImpl.getById(serviceContext, id);
	}
	
	@Override
	public UserDetail getUserDetailByName(ServiceContext serviceContext,
			String userName) {
		User user=getUserByName(serviceContext, userName);
		if(user==null){
			return null;
		}
		UserDetail userDetail=wrapUserDetail(serviceContext, user);
		return userDetail;
	}
	
	private UserDetail wrapUserDetail(ServiceContext serviceContext,User user){
		UserDetail userDetail=new UserDetail();
		userDetail.setUserId(user.getId());
		userDetail.setUserName(user.getUserName());
		userDetail.setStatus(user.getStatus());
		userDetail.setRegisterTime(user.getRegisterTime());

		UserExtend userExtend=getUserExtendByUserId(serviceContext, user.getId());
		if(userExtend!=null){
			userDetail.setNatureName(userExtend.getNatureName());
			userDetail.setUserImage(userExtend.getUserImage());
		}
		
		List<RoleRecord> roles=getRolesByUserId(serviceContext, user.getId());
		userDetail.setRoles(roles);
		
		List<GroupRecord> groups=getGroupsByUserId(serviceContext, user.getId());
		userDetail.setGroups(groups);
		
		return userDetail;
	}
	
	@Override
	public UserDetail getUserDetailById(ServiceContext serviceContext, String id) {
		User user=internalUserServiceImpl.getById(serviceContext, id);
		if(user==null){
			return null;
		}
		UserDetail userDetail=wrapUserDetail(serviceContext, user);
		return userDetail;
	}
	
	
	private JQuery<?> buildUserOnRoleQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select a.id as id"
				+ ",  a.userName as userName"
				+ " , a.status as status"
				+ " , a.registerTime as registerTime"
				+ ", d.natureName as natureName "
				+ " from User a "
				+ " left join UserRole b on a.id=b.userId "
				+ " left join Role c on b.roleId=c.id "
				+ " left join UserExtend d on a.id=d.userId"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("roleId")){
			jpql=jpql+ " and c.id= :roleId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	
	
	@Override
	public List<UserRecord> getUsersByRoleId(
			ServiceContext serviceContext, String roleId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		return buildUserOnRoleQuery(serviceContext, params)
				.models(UserRecord.class);
	}
	
	@Override
	public JPage<UserRecord> getUsersByRoleIdByPage(
			ServiceContext serviceContext, String roleId,
			JSimplePageable simplePageable) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		return buildUserOnRoleQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(UserRecord.class);
	}
	
	
	private JQuery<?> buildUserOnGroupQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select a.id as id"
				+ ",  a.userName as userName"
				+ " , a.status as status"
				+ " , a.registerTime as registerTime"
				+ ", d.natureName as natureName "
				+ " from User a "
				+ " left join UserGroup b on a.id=b.userId "
				+ " left join Group c on b.groupId=c.id "
				+ " left join UserExtend d on a.id=d.userId"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("groupId")){
			jpql=jpql+ " and c.id= :groupId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public List<UserRecord> getUsersByGroupId(
			ServiceContext serviceContext, String groupId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		return buildUserOnGroupQuery(serviceContext, params)
				.models(UserRecord.class);
	}
	
	@Override
	public JPage<UserRecord> getUsersByGroupIdByPage(
			ServiceContext serviceContext, String groupId,
			JSimplePageable simplePageable) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		return buildUserOnGroupQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(UserRecord.class);
	}
	
	private JQuery<?> buildRoleOnGroupQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select a.id as id"
				+ ", a.roleCode as roleCode"
				+ ", a.roleName as roleName "
				+ ", a.description as description "
				+ " from Role a"
				+ " left join RoleGroup b on b.roleId=a.id"
				+ " left join Group c on b.groupId=c.id"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("groupId")){
			jpql=jpql+ " and c.id= :groupId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public List<RoleRecord> getRolesByGroupId(
			ServiceContext serviceContext, String groupId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		return buildRoleOnGroupQuery(serviceContext, params)
				.models(RoleRecord.class);
	}
	
	
	@Override
	public JPage<RoleRecord> getRolesByGroupIdByPage(
			ServiceContext serviceContext, String groupId,
			JSimplePageable simplePageable) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		return buildRoleOnGroupQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(RoleRecord.class);
	}
	
	private JQuery<?> buildGroupOnRoleQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select a.id as id "
				+ ", a.groupCode as groupCode"
				+ ", a.groupName as groupName "
				+ ", a.description as description "
				+ " from Group a"
				+ " left join RoleGroup b on b.groupId=a.id"
				+ " left join Role c on b.roleId=c.id"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("roleId")){
			jpql=jpql+ " and c.id= :roleId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public List<GroupRecord> getGroupsByRoleId(
			ServiceContext serviceContext, String roleId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		return buildGroupOnRoleQuery(serviceContext, params)
				.models(GroupRecord.class);
	}
	
	@Override
	public JPage<GroupRecord> getGroupsByRoleIdByPage(
			ServiceContext serviceContext, String roleId,
			JSimplePageable simplePageable) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		return buildGroupOnRoleQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(GroupRecord.class);
	}
	
	private JQuery<?> buildRoleOnUserQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select c.id as id"
				+ ", c.roleCode as roleCode"
				+ ", c.roleName as roleName"
				+ " from User a "
				+ " left join UserRole b on a.id=b.userId "
				+ " left join Role c on b.roleId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("userId")){
			jpql=jpql+ " and a.id= :userId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public List<RoleRecord> getRolesByUserId(ServiceContext serviceContext,
			String userId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		List<RoleRecord> roles= buildRoleOnUserQuery(serviceContext, params)
				.models(RoleRecord.class);
		return roles;
	}
	
	
	private JQuery<?> buildGroupOnUserQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select c.id as id"
				+ ", c.groupCode as groupCode"
				+ ", c.groupName as groupName"
				+ " from User a "
				+ " left join UserGroup b on a.id=b.userId "
				+ " left join Group c on b.groupId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("userId")){
			jpql=jpql+ " and a.id= :userId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	
	@Override
	public List<GroupRecord> getGroupsByUserId(ServiceContext serviceContext,
			String userId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		List<GroupRecord> groupRecords= buildGroupOnUserQuery(serviceContext, params)
				.models(GroupRecord.class);
		return groupRecords;
	}
	
	private JQuery<?> buildUnbingGroupOnRoleQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select a.id"
				+ " from Group a"
				+ " left join RoleGroup b on b.groupId=a.id"
				+ " left join Role c on b.roleId=c.id"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("roleId")){
			jpql=jpql+ " and c.id= :roleId";
		}
		
		jpql="select o.id as id"
				+ ", o.groupCode as groupCode"
				+ ", o.groupName as groupName"
				+ " from Group o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	
	@Override
	public List<GroupRecord> getUnbingGroupsByRoleId(
			ServiceContext serviceContext, String roleId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		return buildUnbingGroupOnRoleQuery(serviceContext, params)
				.models(GroupRecord.class);
	}
	
	@Override
	public JPage<GroupRecord> getUnbingGroupsByRoleIdByPage(
			ServiceContext serviceContext, String roleId,
			JSimplePageable simplePageable) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		return buildUnbingGroupOnRoleQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(GroupRecord.class);
	}
	
	
	private JQuery<?> buildUnbingGroupOnUserQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select c.id as id"
				+ " from User a "
				+ " left join UserGroup b on a.id=b.userId "
				+ " left join Group c on b.groupId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("userId")){
			jpql=jpql+ " and a.id= :userId";
		}
		jpql="select o.id as id"
				+ ", o.groupCode as groupCode"
				+ ", o.groupName as groupName"
				+ " from Group o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public List<GroupRecord> getUnbingGroupsByUserId(
			ServiceContext serviceContext, String userId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return buildUnbingGroupOnUserQuery(serviceContext, params)
				.models(GroupRecord.class);
	}
	
	
	private JQuery<?> buildUnbingRoleOnGroupQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select a.id as id"
				+ " from Role a"
				+ " left join RoleGroup b on b.roleId=a.id"
				+ " left join Group c on b.groupId=c.id"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("groupId")){
			jpql=jpql+ " and c.id= :groupId";
		}
		
		jpql="select o.id as id"
				+ ", o.roleCode as roleCode"
				+ ", o.roleName as roleName"
				+ " from Role o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public List<RoleRecord> getUnbingRolesByGroupId(
			ServiceContext serviceContext, String groupId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		return buildUnbingRoleOnGroupQuery(serviceContext, params)
				.models(RoleRecord.class);
	}
	
	
	private JQuery<?> buildUnbingUserOnGroupQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select a.id as id"
				+ " from User a "
				+ " left join UserGroup b on a.id=b.userId "
				+ " left join Group c on b.groupId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("groupId")){
			jpql=jpql+ " and c.id= :groupId";
		}
		
		jpql="select oa.id as id"
				+ ",  oa.userName as userName"
				+ " , oa.status as status"
				+ " , oa.registerTime as registerTime"
				+ ", od.natureName as natureName "
				+ " from User oa "
				+ " left join UserExtend od on ou.id=od.userId"
				+ " where oa.deleted='N' and oa.id not in ( "
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public JPage<UserRecord> getUnbingUsersByGroupIdByPage(
			ServiceContext serviceContext, String groupId,
			JSimplePageable simplePageable) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("groupId", groupId);
		return buildUnbingUserOnGroupQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(UserRecord.class);
	}
	
	private JQuery<?> buildUnbingUserOnRoleQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select a.id as id"
				+ " from User a "
				+ " left join UserRole b on a.id=b.userId "
				+ " left join Role c on b.roleId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("roleId")){
			jpql=jpql+ " and c.id= :roleId";
		}
		
		jpql="select oa.id as id"
				+ ",  oa.userName as userName"
				+ " , oa.status as status"
				+ " , oa.registerTime as registerTime"
				+ ", od.natureName as natureName "
				+ " from User oa "
				+ " left join UserExtend od on ou.id=od.userId"
				+ " where oa.deleted='N' and oa.id not in ( "
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public JPage<UserRecord> getUnbingUsersByRoleIdByPage(
			ServiceContext serviceContext, String roleId,
			JSimplePageable simplePageable) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("roleId", roleId);
		return buildUnbingUserOnRoleQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(UserRecord.class);
	}
	
	
	private JQuery<?> buildUnbingRoleOnUserQuery(
			ServiceContext serviceContext, Map<String, Object> params){
		String jpql="select c.id as id"
				+ " from User a "
				+ " left join UserRole b on a.id=b.userId "
				+ " left join Role c on b.roleId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		if(params.containsKey("userId")){
			jpql=jpql+ " and a.id= :userId";
		}
		
		jpql="select o.id as id"
				+ ", o.roleCode as roleCode"
				+ ", o.roleName as roleName"
				+ " from Role o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params);
	}
	
	@Override
	public List<RoleRecord> getUnbingRolesByUserId(
			ServiceContext serviceContext, String userId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		return buildUnbingRoleOnUserQuery(serviceContext, params)
				.models(RoleRecord.class);
	}
	
	
	
	
	
	
	
}
