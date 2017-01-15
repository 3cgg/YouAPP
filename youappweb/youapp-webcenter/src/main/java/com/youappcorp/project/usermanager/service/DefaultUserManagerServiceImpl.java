package com.youappcorp.project.usermanager.service;

import j.jave.platform.jpa.springjpa.query.JCondition.Condition;
import j.jave.platform.jpa.springjpa.query.JJpaDateParam;
import j.jave.platform.jpa.springjpa.query.JQuery;
import j.jave.platform.sps.support.security.subhub.DESedeCipherService;
import j.jave.platform.webcomp.core.service.ServiceSupport;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.utils.JUniqueUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;

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
	public Group getGroupByGroupCode( String groupCode) {
		JAssert.isNotEmpty(groupCode);
		return internalGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("groupCode", groupCode).ready().model();
	}
	
	@Override
	public Group getAdminGroup() {
		return getGroupByGroupCode( ADMIN_CODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Group getDefaultGroup() {
		return getGroupByGroupCode( DEFAULT_CODE);
	}
	
	@Override
	public boolean isDefaultGroup( String groupId) {
		Group role=internalGroupServiceImpl.getById( groupId);
		return DEFAULT_CODE.equalsIgnoreCase(role.getGroupCode());
	}
	
	@Override
	public boolean isDefaultGroupCode( String code) {
		return DEFAULT_CODE.equalsIgnoreCase(code);
	}
	
	public boolean isAdminGroup( String groupId) {
		Group role=internalGroupServiceImpl.getById( groupId);
		return ADMIN_CODE.equalsIgnoreCase(role.getGroupCode());
	}
	
	@Override
	public boolean isAdminGroupCode( String code) {
		return ADMIN_CODE.equalsIgnoreCase(code);
	}
	
	@Override
	public JPage<Group> getGroupsByPage(
			GroupSearchCriteria groupSearchCriteria, JSimplePageable simplePageable){
		return internalGroupServiceImpl.singleEntityQuery().conditionDefault()
				.likes("groupCode", groupSearchCriteria.getGroupCode())
				.likes("groupName", groupSearchCriteria.getGroupName())
				.likes("description", groupSearchCriteria.getDescription())
				.ready().modelPage(simplePageable);
	}
	
	@Override
	public List<Group> getAllGroups() {
		return internalGroupServiceImpl.singleEntityQuery()
				.conditionDefault().ready().models();
	}
	
	
	@Override
	public void saveGroup( Group group)
			throws BusinessException {
		try{
			validateGroupCode(group);
			
			if(exists( group)){
				throw new BusinessException("group code ["+group.getGroupCode()+"] already has exist.");
			}
			internalGroupServiceImpl.saveOnly( group);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean exists( Group group)
			throws BusinessException {
		boolean exists=false;
		try{
			if(group==null){
				throw new IllegalArgumentException("role argument is null");
			}
			
			Group dbGroup=getGroupByGroupCode( group.getGroupCode());
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
	public void updateGroup( Group group)
			throws BusinessException {
		try{
			validateGroupCode(group);
			
			if(JStringUtils.isNullOrEmpty(group.getId())){
				throw new IllegalArgumentException("the primary property id of group is null.");
			}
			
			if(exists( group)){
				throw new BusinessException("group code ["+group.getGroupCode()+"] already has exist.");
			}
			
			Group dbGroup=getGroupById( group.getId());
			
			if(ADMIN_CODE.equalsIgnoreCase(dbGroup.getGroupCode())){
				throw new BusinessException("group code ["+ADMIN_CODE+"] is initialized by system.please change...");
			}
			
			if(DEFAULT_CODE.equalsIgnoreCase(dbGroup.getGroupCode())){
				throw new BusinessException("group code ["+DEFAULT_CODE+"] is initialized by system.please change...");
			}
			
			dbGroup.setGroupCode(group.getGroupCode());
			dbGroup.setGroupName(group.getGroupName());
			dbGroup.setDescription(group.getDescription());
			internalGroupServiceImpl.updateOnly( dbGroup);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void deleteGroup( Group group)
			throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(group.getId())){
				throw new IllegalArgumentException("the primary property id of group is null.");
			}
			
			if(JStringUtils.isNullOrEmpty(group.getGroupCode())){
				group.setGroupCode(internalGroupServiceImpl.getById( group.getId()).getGroupCode());
			}
			
			validateGroupCode(group);
			internalGroupServiceImpl.delete( group.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Autowired
	private InternalRoleServiceImpl internalRoleServiceImpl;
	
	@Override
	public Role getRoleByRoleCode( String roleCode) {
		return internalRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("roleCode", roleCode).ready().model();
	}
	
	@Override
	public Role getAdminRole() {
		return getRoleByRoleCode( ADMIN_CODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getDefaultRole() {
		return getRoleByRoleCode( DEFAULT_CODE);
	}
	
	@Override
	public boolean isDefaultRole( String roleId) {
		Role role=internalRoleServiceImpl.getById( roleId);
		return DEFAULT_CODE.equalsIgnoreCase(role.getRoleCode());
	}
	
	@Override
	public boolean isDefaultRoleCode( String code) {
		return DEFAULT_CODE.equalsIgnoreCase(code);
	}
	
	@Override
	public boolean isAdminRole( String roleId) {
		Role role=internalRoleServiceImpl.getById( roleId);
		return ADMIN_CODE.equalsIgnoreCase(role.getRoleCode());
	}
	
	@Override
	public boolean isAdminRoleCode( String code) {
		return ADMIN_CODE.equalsIgnoreCase(code);
	}
	
	@Override
	public JPage<Role> getAllRolesByPage(
			RoleSearchCriteria roleSearchCriteria,
			JSimplePageable simplePageable) {
		return internalRoleServiceImpl.singleEntityQuery().conditionDefault()
				.likes("roleCode", roleSearchCriteria.getRoleCode())
				.likes("roleName", roleSearchCriteria.getRoleName())
				.likes("description", roleSearchCriteria.getDescription())
				.ready().modelPage(simplePageable);
	}
	
	@Override
	public List<Role> getAllRoles() {
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
	public void saveRole( Role role)
			throws BusinessException {
		
		try{

			validateRoleCode(role);
			if(exists( role)){
				throw new BusinessException("role code ["+role.getRoleCode()+"] already has exist.");
			}
			internalRoleServiceImpl.saveOnly( role);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean exists( Role role)
			throws BusinessException {
		if(role==null){
			throw new IllegalArgumentException("role argument is null");
		}
		boolean exists=false;
		Role dbRole=getRoleByRoleCode( role.getRoleCode());
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
	public void updateRole( Role role)
			throws BusinessException {
		try{
			validateRoleCode(role);
			
			if(JStringUtils.isNullOrEmpty(role.getId())){
				throw new IllegalArgumentException("the primary property id of role is null.");
			}
			
			if(exists( role)){
				throw new BusinessException("role code ["+role.getRoleCode()+"] already has exist.");
			}
			
			Role dbRole=getRoleById( role.getId());
			
			if(ADMIN_CODE.equalsIgnoreCase(dbRole.getRoleCode())){
				throw new BusinessException("group code ["+ADMIN_CODE+"] is initialized by system.please change...");
			}
			
			if(DEFAULT_CODE.equalsIgnoreCase(dbRole.getRoleCode())){
				throw new BusinessException("group code ["+DEFAULT_CODE+"] is initialized by system.please change...");
			}
			
			dbRole.setRoleCode(role.getRoleCode());
			dbRole.setRoleName(role.getRoleName());
			dbRole.setDescription(role.getDescription());
			
			internalRoleServiceImpl.updateOnly( dbRole);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void deleteRole( Role role)
			throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(role.getId())){
				throw new IllegalArgumentException("the primary property id of role is null.");
			}
			
			if(JStringUtils.isNullOrEmpty(role.getRoleCode())){
				role.setRoleCode(internalRoleServiceImpl.getById( role.getId()).getRoleCode());
			}
			validateRoleCode(role);
			internalRoleServiceImpl.delete( role.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public Role getRoleById( String id) {
		return internalRoleServiceImpl.getById( id);
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
	public void saveUser( User user) throws BusinessException {
		try{
			internalUserServiceImpl.saveOnly( user);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void updateUser( User user)
			throws BusinessException {
		try{
			internalUserServiceImpl.updateOnly( user);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void updateUser( User user,
			UserExtend userExtend) throws BusinessException {
//		updateUser( user);
		UserExtend dbUserExtend=getUserExtendByUserId( user.getId());
		dbUserExtend.setNatureName(userExtend.getNatureName());
		updateUserExtend( dbUserExtend);
	}
	
	@Override
	public void deleteUser( String userId) {
		internalUserServiceImpl.delete( userId);
	}
	
	@Override
	public User getUserByName( String userName) {
		return internalUserServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userName", userName).ready().model();
	}
	
	
	private JQuery<?> buildUserQuery(
			 Map<String, Condition> params){
		String jpql="select a.id as id"
				+ ",  a.userName as userName"
				+ " , a.status as status"
				+ " , a.registerTime as registerTime"
				+ ", d.natureName as natureName "
				+ " from User a "
				+ " left join UserExtend d on a.id=d.userId"
				+ " where a.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("status"))!=null){
			jpql=jpql+" and a.status "+condition.getOpe()+" :status";
		}
		if((condition=params.get("userName"))!=null){
			jpql=jpql+" and a.userName "+condition.getOpe()+" :userName";
		}
		if((condition=params.get("natureName"))!=null){
			jpql=jpql+" and d.natureName "+condition.getOpe()+" :natureName";
		}

		if((condition=params.get("registerTimeStart"))!=null){
			jpql=jpql+" and a.registerTime > :registerTimeStart";
		}
		
		if((condition=params.get("registerTimeEnd"))!=null){
			jpql=jpql+" and a.registerTime < :registerTimeEnd";
		}

		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public JPage<UserRecord> getUsersByPage( UserSearchCriteria userSearchCriteria,
			JSimplePageable simplePageable) {
		
		Map<String, Condition> params=new HashMap<String, Condition>();
		String status=userSearchCriteria.getStatus();
		if(JStringUtils.isNotNullOrEmpty(status)){
			params.put("status", Condition.equal(status));
		}
		
		String userName=userSearchCriteria.getUserName();
		if(JStringUtils.isNotNullOrEmpty(userName)){
			params.put("userName", Condition.likes(userName));
		}
		
		String natureName=userSearchCriteria.getNatureName();
		if(JStringUtils.isNotNullOrEmpty(natureName)){
			params.put("natureName", Condition.likes(natureName));
		}
		
		String registerTimeStart=userSearchCriteria.getRegisterTimeStart();
		if(JStringUtils.isNotNullOrEmpty(registerTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(registerTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("registerTimeStart", Condition.larger(dateParam));
		}
		
		String registerTimeEnd=userSearchCriteria.getRegisterTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(registerTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			Date date=JDateUtils.parseDate(registerTimeEnd);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			dateParam.setDate(calendar.getTime());
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("registerTimeEnd", Condition.smaller(dateParam));
		}
		
		return buildUserQuery( params)
				.modelPage(simplePageable,UserRecord.class);
	}
	
	@Override
	public UserRecord getUserById( String id) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("userId", Condition.equal(id));
		return buildUserQuery( params)
				.model(UserRecord.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsers() {
		return internalUserServiceImpl.singleEntityQuery().conditionDefault().ready().models();
	}

	
	@Override
	public void register(User user,UserExtend userExtend) throws BusinessException {
		try{
			
			if(JStringUtils.isNullOrEmpty(user.getUserName())){
				throw new BusinessException("用户名不能为空");
			}
			
			if(JStringUtils.isNullOrEmpty(user.getPassword())){
				throw new BusinessException("密码不能为空");
			}		
			
			User dbUser=getUserByName( user.getUserName().trim());
			if(dbUser!=null){
				throw new BusinessException("用户已经存在");
			}
			
			String passwrod=user.getPassword().trim();
			String encriptPassword=deSedeCipherService.encrypt(passwrod);
			user.setPassword(encriptPassword);
			user.setUserName(user.getUserName().trim());
			user.setStatus("ACTIVE");
			user.setRegisterTime(new Timestamp(new Date().getTime()));
			saveUser( user);  // with encrypted password
			
			//save user extend
			userExtend.setUserId(user.getId());
			saveUserExtend( userExtend);
			
			//save role
			UserRole userRole=new UserRole();
			userRole.setRoleId(getDefaultRole().getId());
			userRole.setUserId(user.getId());
			internalUserRoleServiceImpl.saveOnly( userRole);
			
			//save group
			UserGroup userGroup=new UserGroup();
			userGroup.setGroupId(getDefaultGroup().getId());
			userGroup.setUserId(user.getId());
			internalUserGroupServiceImpl.saveOnly( userGroup);
			
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	
	@Override
	public void resetPassword( String userId,String password) {
		try {
			User dbUser=internalUserServiceImpl.getById( userId);
			if(dbUser==null){
				throw new BusinessException("用户不存在");
			}
			
			String passwrod=password.trim();
			String encriptPassword=deSedeCipherService.encrypt(passwrod);
			
			dbUser.setPassword(encriptPassword);
			internalUserServiceImpl.saveOnly( dbUser);
		} catch (Exception e) {
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	
	
	
	
	
	@Autowired
	private InternalRoleGroupServiceImpl internalRoleGroupServiceImpl;
	
	@Override
	public void bingRoleGroup(String roleId,String groupId) throws BusinessException {
		
		try{
			if(isBingRoleAndGroup( roleId, groupId)){
				throw new BusinessException("the role had already belong to the group.");
			}
			
			RoleGroup roleGroup=new RoleGroup();
			roleGroup.setRoleId(roleId);
			roleGroup.setGroupId(groupId);
			roleGroup.setId(JUniqueUtils.unique());
			internalRoleGroupServiceImpl.saveOnly( roleGroup);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void unbingRoleGroup(String roleId,String groupId) 
			throws BusinessException {
		
		try{

			RoleGroup userGroup=getRoleGroupOnRoleIdAndGroupId( roleId, groupId);
			if(userGroup==null){
				throw new BusinessException("the user doesnot belong to the group.");
			}
			internalRoleGroupServiceImpl.delete( userGroup.getId());
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public RoleGroup getRoleGroupOnRoleIdAndGroupId(
			 String roleId, String groupId) {
		return internalRoleGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("roleId", roleId)
				.equals("groupId", groupId)
				.ready().model();
	}
	
	@Override
	public long countOnRoleIdAndGroupId(
			String roleId, String groupId) {
		return internalRoleGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("roleId", roleId)
				.equals("groupId", groupId)
				.ready().count();
	}
	
	@Override
	public boolean isBingRoleAndGroup(String roleId,String groupId) {
		long count=countOnRoleIdAndGroupId( roleId, groupId);
		return count>0;
	}

	@Override
	public List<RoleGroup> getRoleGroupsByRoleId(
			String roleId) {
		return internalRoleGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("roleId", roleId)
				.ready().models();
	}

	@Override
	public List<RoleGroup> getRoleGroupsByGroupId(
			 String groupId) {
		return internalRoleGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("groupId", groupId)
				.ready().models();
	}
	
	
	@Autowired
	private InternalUserRoleServiceImpl internalUserRoleServiceImpl;
	
	@Override
	public List<UserRole> getUserRolesByUserId(
			String userId) {
		return internalUserRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().models();
	}
	
	@Override
	public void bingUserRole( String userId,
			String roleId) throws BusinessException {
		
		try{
			if(isBingUserAndRole( userId, roleId)){
				throw new BusinessException("the user had already the role.");
			}
			
			UserRole userRole=new UserRole();
			userRole.setUserId(userId);
			userRole.setRoleId(roleId);
			userRole.setId(JUniqueUtils.unique());
			internalUserRoleServiceImpl.saveOnly( userRole);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
		
	}
	
	@Override
	public void unbingUserRole( String userId,
			String roleId) throws BusinessException {
		try{
			UserRole userRole=getUserRoleOnUserIdAndRoleId( userId, roleId);
			if(userRole==null){
				throw new BusinessException("the user doesnot have the role.");
			}
			internalUserRoleServiceImpl.delete( userRole.getId());
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public UserRole getUserRoleOnUserIdAndRoleId(
			String userId, String roleId) {
		return internalUserRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId)
				.equals("roleId", roleId)
				.ready().model();
	}
	
	@Override
	public boolean isBingUserAndRole( String userId,
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
	public void saveUserExtend( UserExtend userExtend)
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
				UserExtend dbUserExtend=getUserExtendByNatureName( userExtend.getNatureName());
				if(dbUserExtend!=null){
					throw new BusinessException("nature name already exists, please change others.");
				}
			}
			
			internalUserExtendServiceImpl.saveOnly( userExtend);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateUserExtend( UserExtend userExtend)
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
				UserExtend dbUserExtend=getUserExtendByNatureName( userExtend.getNatureName());
				if(dbUserExtend!=null&&!userExtend.getId().equals(dbUserExtend.getId())){
					throw new BusinessException("nature name already exists, please change others.");
				}
			}
			
			internalUserExtendServiceImpl.updateOnly( userExtend);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
		
	}

	@Override
	public UserExtend getUserExtendByUserId(
			String userId) {
		return internalUserExtendServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().model();
	}

	@Override
	public UserExtend getUserExtendByNatureName(
			String natureName) {
		return internalUserExtendServiceImpl.singleEntityQuery().conditionDefault()
				.equals("natureName", natureName).ready().model();
	}

	
	@Autowired
	private InternalUserGroupServiceImpl internalUserGroupServiceImpl;

	@Override
	public List<UserGroup> getUserGroupsByUserId(
			String userId) {
		return internalUserGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().models();
	}
	
	@Override
	public void bingUserGroup( String userId,
			String groupId) throws BusinessException {
		
		try{

			if(isBingUserAndGroup( userId, groupId)){
				throw new BusinessException("the user had already belong to the group.");
			}
			
			UserGroup userGroup=new UserGroup();
			userGroup.setUserId(userId);
			userGroup.setGroupId(groupId);
			userGroup.setId(JUniqueUtils.unique());
			internalUserGroupServiceImpl.saveOnly( userGroup);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void unbingUserGroup( String userId,
			String groupId) throws BusinessException {
		try{
			UserGroup userGroup=getUserGroupOnUserIdAndGroupId( userId, groupId);
			if(userGroup==null){
				throw new BusinessException("the user doesnot belong to the group.");
			}
			internalUserGroupServiceImpl.delete( userGroup.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public UserGroup getUserGroupOnUserIdAndGroupId(
			String userId, String groupId) {
		return internalUserGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId)
				.equals("groupId", groupId)
				.ready().model();
	}
	
	@Override
	public boolean isBingUserAndGroup( String userId,
			String groupId) {
		long count=internalUserGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId)
				.equals("groupId", groupId)
				.ready().count();
		return count>0;
	}
	
	@Override
	public Group getGroupById( String id) {
		return internalGroupServiceImpl.getById( id);
	}
	
	@Override
	public UserDetail getUserDetailByName(
			String userName) {
		User user=getUserByName( userName);
		if(user==null){
			return null;
		}
		UserDetail userDetail=wrapUserDetail( user);
		return userDetail;
	}
	
	private UserDetail wrapUserDetail(User user){
		UserDetail userDetail=new UserDetail();
		userDetail.setUserId(user.getId());
		userDetail.setUserName(user.getUserName());
		userDetail.setStatus(user.getStatus());
		userDetail.setRegisterTime(user.getRegisterTime());

		UserExtend userExtend=getUserExtendByUserId( user.getId());
		if(userExtend!=null){
			userDetail.setNatureName(userExtend.getNatureName());
			userDetail.setUserImage(userExtend.getUserImage());
		}
		
		List<RoleRecord> roles=getRolesByUserId( user.getId());
		userDetail.setRoles(roles);
		
		List<GroupRecord> groups=getGroupsByUserId( user.getId());
		userDetail.setGroups(groups);
		
		return userDetail;
	}
	
	@Override
	public UserDetail getUserDetailById( String id) {
		User user=internalUserServiceImpl.getById( id);
		if(user==null){
			return null;
		}
		UserDetail userDetail=wrapUserDetail( user);
		return userDetail;
	}
	
	
	private JQuery<?> buildUserOnRoleQuery(
			 Map<String, Condition> params){
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
		Condition condition=null;
		if((condition=params.get("roleId"))!=null){
			jpql=jpql+ " and c.id "+condition.getOpe()+" :roleId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	
	
	@Override
	public List<UserRecord> getUsersByRoleId(
			 String roleId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("roleId", Condition.equal(roleId));
		return buildUserOnRoleQuery( params)
				.models(UserRecord.class);
	}
	
	@Override
	public JPage<UserRecord> getUsersByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("roleId", Condition.equal(roleId));
		return buildUserOnRoleQuery( params)
				.setPageable(simplePageable)
				.modelPage(UserRecord.class);
	}
	
	
	private JQuery<?> buildUserOnGroupQuery(
			 Map<String, Condition> params){
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
		Condition condition=null;
		if((condition=params.get("groupId"))!=null){
			jpql=jpql+ " and c.id "+condition.getOpe()+"  :groupId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public List<UserRecord> getUsersByGroupId(
			 String groupId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("groupId", Condition.equal(groupId));
		return buildUserOnGroupQuery( params)
				.models(UserRecord.class);
	}
	
	@Override
	public JPage<UserRecord> getUsersByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("groupId", Condition.equal(groupId));
		return buildUserOnGroupQuery( params)
				.setPageable(simplePageable)
				.modelPage(UserRecord.class);
	}
	
	private JQuery<?> buildRoleOnGroupQuery(
			 Map<String, Condition> params){
		String jpql="select a.id as id"
				+ ", a.roleCode as roleCode"
				+ ", a.roleName as roleName "
				+ ", a.description as description "
				+ " from Role a"
				+ " left join RoleGroup b on b.roleId=a.id"
				+ " left join Group c on b.groupId=c.id"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("groupId"))!=null){
			jpql=jpql+ " and c.id "+condition.getOpe()+" :groupId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public List<RoleRecord> getRolesByGroupId(
			 String groupId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("groupId", Condition.equal(groupId));
		return buildRoleOnGroupQuery( params)
				.models(RoleRecord.class);
	}
	
	
	@Override
	public JPage<RoleRecord> getRolesByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("groupId", Condition.equal(groupId));
		return buildRoleOnGroupQuery( params)
				.setPageable(simplePageable)
				.modelPage(RoleRecord.class);
	}
	
	private JQuery<?> buildGroupOnRoleQuery(
			 Map<String, Condition> params){
		String jpql="select a.id as id "
				+ ", a.groupCode as groupCode"
				+ ", a.groupName as groupName "
				+ ", a.description as description "
				+ " from Group a"
				+ " left join RoleGroup b on b.groupId=a.id"
				+ " left join Role c on b.roleId=c.id"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("roleId"))!=null){
			jpql=jpql+ " and c.id "+condition.getOpe()+" :roleId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public List<GroupRecord> getGroupsByRoleId(
			 String roleId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("roleId", Condition.equal(roleId));
		return buildGroupOnRoleQuery( params)
				.models(GroupRecord.class);
	}
	
	@Override
	public JPage<GroupRecord> getGroupsByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("roleId", Condition.equal(roleId));
		return buildGroupOnRoleQuery( params)
				.setPageable(simplePageable)
				.modelPage(GroupRecord.class);
	}
	
	private JQuery<?> buildRoleOnUserQuery(
			 Map<String, Condition> params){
		String jpql="select c.id as id"
				+ ", c.roleCode as roleCode"
				+ ", c.roleName as roleName"
				+ ", c.description as description"
				+ " from User a "
				+ " left join UserRole b on a.id=b.userId "
				+ " left join Role c on b.roleId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("userId"))!=null){
			jpql=jpql+ " and a.id "+condition.getOpe()+" :userId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public List<RoleRecord> getRolesByUserId(
			String userId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("userId", Condition.equal(userId));
		List<RoleRecord> roles= buildRoleOnUserQuery( params)
				.models(RoleRecord.class);
		return roles;
	}
	
	
	private JQuery<?> buildGroupOnUserQuery(
			 Map<String, Condition> params){
		String jpql="select c.id as id"
				+ ", c.groupCode as groupCode"
				+ ", c.groupName as groupName"
				+ ", c.description as description"
				+ " from User a "
				+ " left join UserGroup b on a.id=b.userId "
				+ " left join Group c on b.groupId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("userId"))!=null){
			jpql=jpql+ " and a.id "+condition.getOpe()+" :userId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	
	@Override
	public List<GroupRecord> getGroupsByUserId(
			String userId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("userId", Condition.equal(userId));
		List<GroupRecord> groupRecords= buildGroupOnUserQuery( params)
				.models(GroupRecord.class);
		return groupRecords;
	}
	
	private JQuery<?> buildUnbingGroupOnRoleQuery(
			 Map<String, Condition> params){
		String jpql="select a.id"
				+ " from Group a"
				+ " left join RoleGroup b on b.groupId=a.id"
				+ " left join Role c on b.roleId=c.id"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("roleId"))!=null){
			jpql=jpql+ " and c.id "+condition.getOpe()+" :roleId";
		}
		
		jpql="select o.id as id"
				+ ", o.groupCode as groupCode"
				+ ", o.groupName as groupName"
				+ " from Group o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	
	@Override
	public List<GroupRecord> getUnbingGroupsByRoleId(
			 String roleId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("roleId", Condition.equal(roleId));
		return buildUnbingGroupOnRoleQuery( params)
				.models(GroupRecord.class);
	}
	
	@Override
	public JPage<GroupRecord> getUnbingGroupsByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("roleId", Condition.equal(roleId));
		return buildUnbingGroupOnRoleQuery( params)
				.setPageable(simplePageable)
				.modelPage(GroupRecord.class);
	}
	
	
	private JQuery<?> buildUnbingGroupOnUserQuery(
			 Map<String, Condition> params){
		String jpql="select c.id as id"
				+ " from User a "
				+ " left join UserGroup b on a.id=b.userId "
				+ " left join Group c on b.groupId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("userId"))!=null){
			jpql=jpql+ " and a.id "+condition.getOpe()+" :userId";
		}
		jpql="select o.id as id"
				+ ", o.groupCode as groupCode"
				+ ", o.groupName as groupName"
				+ " from Group o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public List<GroupRecord> getUnbingGroupsByUserId(
			 String userId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("userId", Condition.equal(userId));
		return buildUnbingGroupOnUserQuery( params)
				.models(GroupRecord.class);
	}
	
	
	private JQuery<?> buildUnbingRoleOnGroupQuery(
			 Map<String, Condition> params){
		String jpql="select a.id as id"
				+ " from Role a"
				+ " left join RoleGroup b on b.roleId=a.id"
				+ " left join Group c on b.groupId=c.id"
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("groupId"))!=null){
			jpql=jpql+ " and c.id "+condition.getOpe()+" :groupId";
		}
		
		jpql="select o.id as id"
				+ ", o.roleCode as roleCode"
				+ ", o.roleName as roleName"
				+ " from Role o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public List<RoleRecord> getUnbingRolesByGroupId(
			 String groupId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("groupId", Condition.equal(groupId));
		return buildUnbingRoleOnGroupQuery( params)
				.models(RoleRecord.class);
	}
	
	
	private JQuery<?> buildUnbingUserOnGroupQuery(
			 Map<String, Condition> params){
		String jpql="select a.id as id"
				+ " from User a "
				+ " left join UserGroup b on a.id=b.userId "
				+ " left join Group c on b.groupId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("groupId"))!=null){
			jpql=jpql+ " and c.id "+condition.getOpe()+" :groupId";
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
				.setParams(params(params));
	}
	
	@Override
	public JPage<UserRecord> getUnbingUsersByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("groupId", Condition.equal(groupId));
		return buildUnbingUserOnGroupQuery( params)
				.setPageable(simplePageable)
				.modelPage(UserRecord.class);
	}
	
	private JQuery<?> buildUnbingUserOnRoleQuery(
			 Map<String, Condition> params){
		String jpql="select a.id as id"
				+ " from User a "
				+ " left join UserRole b on a.id=b.userId "
				+ " left join Role c on b.roleId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("roleId"))!=null){
			jpql=jpql+ " and c.id "+condition.getOpe()+" :roleId";
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
				.setParams(params(params));
	}
	
	@Override
	public JPage<UserRecord> getUnbingUsersByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("roleId", Condition.equal(roleId));
		return buildUnbingUserOnRoleQuery( params)
				.setPageable(simplePageable)
				.modelPage(UserRecord.class);
	}
	
	
	private JQuery<?> buildUnbingRoleOnUserQuery(
			 Map<String, Condition> params){
		String jpql="select c.id as id"
				+ " from User a "
				+ " left join UserRole b on a.id=b.userId "
				+ " left join Role c on b.roleId=c.id "
				+ " where a.deleted='N' and c.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("userId"))!=null){
			jpql=jpql+ " and a.id "+condition.getOpe()+" :userId";
		}
		
		jpql="select o.id as id"
				+ ", o.roleCode as roleCode"
				+ ", o.roleName as roleName"
				+ " from Role o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public List<RoleRecord> getUnbingRolesByUserId(
			 String userId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("userId", Condition.equal(userId));
		return buildUnbingRoleOnUserQuery( params)
				.models(RoleRecord.class);
	}
	
	
	
	
	
	
	
}
