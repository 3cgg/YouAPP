package com.youappcorp.project.usermanager.controller;

import me.bunny.app._c._web.web.model.ResponseModel;
import me.bunny.app._c._web.web.youappmvc.controller.SimpleControllerSupport;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JPageImpl;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel._c.utils.JObjectUtils;
import me.bunny.kernel._c.utils.JStringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.Group;
import com.youappcorp.project.usermanager.model.GroupRecord;
import com.youappcorp.project.usermanager.model.Role;
import com.youappcorp.project.usermanager.model.RoleRecord;
import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserExtend;
import com.youappcorp.project.usermanager.model.UserRecord;
import com.youappcorp.project.usermanager.model.UserTracker;
import com.youappcorp.project.usermanager.service.DefaultUserManagerServiceImpl;
import com.youappcorp.project.usermanager.service.UserTrackerService;
import com.youappcorp.project.usermanager.vo.GroupCreateInVO;
import com.youappcorp.project.usermanager.vo.GroupEditInVO;
import com.youappcorp.project.usermanager.vo.GroupRecordOutVO;
import com.youappcorp.project.usermanager.vo.GroupSearchCriteria;
import com.youappcorp.project.usermanager.vo.ResetPasswordVO;
import com.youappcorp.project.usermanager.vo.RoleCreateInVO;
import com.youappcorp.project.usermanager.vo.RoleEditInVO;
import com.youappcorp.project.usermanager.vo.RoleGroupInVO;
import com.youappcorp.project.usermanager.vo.RoleRecordOutVO;
import com.youappcorp.project.usermanager.vo.RoleSearchCriteria;
import com.youappcorp.project.usermanager.vo.TimeLineGroup;
import com.youappcorp.project.usermanager.vo.TimelineView;
import com.youappcorp.project.usermanager.vo.UserEditInVO;
import com.youappcorp.project.usermanager.vo.UserGroupInVO;
import com.youappcorp.project.usermanager.vo.UserRecordOutVO;
import com.youappcorp.project.usermanager.vo.UserRegisterInVO;
import com.youappcorp.project.usermanager.vo.UserRoleInVO;
import com.youappcorp.project.usermanager.vo.UserSearchCriteria;

@Controller
@RequestMapping(value="/usermanager")
public class UserManagerController extends SimpleControllerSupport {
	
	@Autowired
	private UserTrackerService userTrackerService;
	
	@Autowired
	private DefaultUserManagerServiceImpl userManagerService;
	
	@ResponseBody
	@RequestMapping(value="/getTimeline")
	public ResponseModel getTimeline(
			@RequestParam("userName")String userName) {
		List<UserTracker> userTrackers= userTrackerService.getUserTrackerByName(userName);
		List<TimeLineGroup> timeLineGroups=new ArrayList<TimeLineGroup>();
		if(userTrackers!=null){
			String doDate="";
			TimeLineGroup doTimeLineGroup=null;
			for (Iterator<UserTracker> iterator = userTrackers.iterator(); iterator
					.hasNext();) {
				UserTracker userTracker =  iterator.next();
				String date=JDateUtils.format(userTracker.getLoginTime());
				if(!date.equals(doDate)){
					// new group 
					doTimeLineGroup=new TimeLineGroup();
					doTimeLineGroup.setDate(date);
					doDate=date;
					timeLineGroups.add(doTimeLineGroup);
				}
				
				TimelineView timelineView=new TimelineView();
				// fillin highlight 
				timelineView.setHighlightContent(userTracker.getIp());
				timelineView.setHighlightPath("/login.loginaction/profile");
				// fillin header 
				String header=userTracker.getLoginClient();
				timelineView.setHeader(header);
				//fillin timeoffset
				timelineView.setTimeOffset(JDateUtils.getTimeOffset(userTracker.getLoginTime()));
				doTimeLineGroup.getTimelineViews().add(timelineView);
			}
		}
		return ResponseModel.newSuccess().setData(timeLineGroups);
	}
	
	@ResponseBody
	@RequestMapping(value="/register")
	public ResponseModel register(UserRegisterInVO userRegisterInVO){
		
		if(!userRegisterInVO.getPassword().equals(userRegisterInVO.getRetypePassword())){
			throw new BusinessException("两次输入的密码不一样");
		}
		
		User user=new User();
		user.setPassword(userRegisterInVO.getPassword());
		user.setUserName(userRegisterInVO.getUserName());
		UserExtend userExtend=new UserExtend();
		userExtend.setNatureName(userRegisterInVO.getNatureName());
		userExtend.setUserName(userRegisterInVO.getUserName());
		userManagerService.register( user, userExtend);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value="/resetPassword")
	public ResponseModel resetPassword( ResetPasswordVO resetPasswordVO){
		
		if(JStringUtils.isNullOrEmpty(resetPasswordVO.getPassword())){
			throw new BusinessException("密码不能为空");
		}
		
		userManagerService.resetPassword( resetPasswordVO.getUserId(), resetPasswordVO.getPassword());
		return ResponseModel.newSuccess();
	}
	
	
	@RequestMapping("/updateUser")
	public ResponseModel updateUser(UserEditInVO userEditInVO ){
		User user= new User();
		user.setId(userEditInVO.getId());
		
		UserExtend userExtend=new UserExtend();
		userExtend.setNatureName(userEditInVO.getNatureName());
		userExtend.setUserImage(userEditInVO.getUserImage());
		userManagerService.updateUser( user,userExtend);
		return ResponseModel.newSuccess();
	}
	
	@RequestMapping("/deleteUser")
	public ResponseModel deleteUser(String id){
		userManagerService.deleteUser( id);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value="/getUsersByPage")
	public ResponseModel getUsersByPage(UserSearchCriteria userSearchCriteria,JSimplePageable simplePageable){
		JPage<UserRecord> page= userManagerService.getUsersByPage( userSearchCriteria, simplePageable);
		page.setContent(toUserRecordOutVO(page.getContent()));
		return ResponseModel.newSuccess().setData(page);
	}
	
	@RequestMapping("/getUserById")
	public ResponseModel getUserById(String id){
		UserRecord userRecord=userManagerService.getUserById( id);
		return ResponseModel.newSuccess(toUserRecordOutVO(userRecord));
	}
	
	@RequestMapping("/saveRole")
	public ResponseModel saveRole(RoleCreateInVO roleCreateInVO){
		Role role=JObjectUtils.simpleCopy(roleCreateInVO, Role.class);
		userManagerService.saveRole( role);
		return ResponseModel.newSuccess();
	}
	
	@RequestMapping("/updateRole")
	public ResponseModel updateRole(RoleEditInVO roleEditInVO){
		Role role=JObjectUtils.simpleCopy(roleEditInVO, Role.class);
		userManagerService.updateRole( role);
		return ResponseModel.newSuccess();
	}
	
	@RequestMapping("/deleteRole")
	public ResponseModel deleteRole(String id){
		userManagerService.deleteRole( userManagerService.getRoleById( id));
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value="/getRolesByPage")
	public ResponseModel getRolesByPage(RoleSearchCriteria roleSearchCriteria,JSimplePageable simplePageable){
		JPage<Role> rolesPage= userManagerService.getAllRolesByPage( roleSearchCriteria, simplePageable);
		toRoleViewPage(rolesPage);
		return ResponseModel.newSuccess().setData(rolesPage);
	}
	
	@RequestMapping("/getRoleById")
	public ResponseModel getRoleById(String id){
		Role role=userManagerService.getRoleById( id);
		RoleRecord roleRecord= genRoleRecordOutVO(role);
		return ResponseModel.newSuccess(toRoleRecordOutVO(roleRecord));
	}
	
	@RequestMapping("/getGroupsByRoleId")
	public ResponseModel getGroupsByRoleId(String roleId){
		List<GroupRecord> groupRecords = userManagerService.getGroupsByRoleId( roleId);
		List<GroupRecordOutVO> groupRecordOutVOs = toGroupRecordOutVO(groupRecords);
		return ResponseModel.newSuccess(groupRecordOutVOs);
	}

	@RequestMapping("/getGroupsByRoleIdByPage")
	public ResponseModel getGroupsByRoleIdByPage(String roleId,JSimplePageable simplePageable){
		JPage<GroupRecord> page = userManagerService.getGroupsByRoleIdByPage( roleId, simplePageable);
		page.setContent(toGroupRecordOutVO(page.getContent()));
		return ResponseModel.newSuccess(page);
	}
	
	@RequestMapping("/getUnbingGroupsByRoleIdByPage")
	public ResponseModel getUnbingGroupsByRoleIdByPage(String roleId,JSimplePageable simplePageable){
		JPage<GroupRecord> page = userManagerService.getUnbingGroupsByRoleIdByPage( roleId, simplePageable);
		page.setContent(toGroupRecordOutVO(page.getContent()));
		return ResponseModel.newSuccess(page);
	}
	
	private List<GroupRecordOutVO> toGroupRecordOutVO(
			List<GroupRecord> groupRecords) {
		List<GroupRecordOutVO> groupRecordOutVOs=new ArrayList<GroupRecordOutVO>();
		for(int i=0;i<groupRecords.size();i++){
			GroupRecord groupRecord=groupRecords.get(i);
			groupRecordOutVOs.add(toGroupRecordOutVO(groupRecord));
		}
		return groupRecordOutVOs;
	}

	private GroupRecordOutVO toGroupRecordOutVO(GroupRecord groupRecord) {
		return JObjectUtils.simpleCopy(groupRecord, GroupRecordOutVO.class);
	}
	
	private void toRoleViewPage(JPage<Role> rolesPage) {
		List<Role> roles=rolesPage.getContent();
		List<RoleRecordOutVO> roleRecordOutVOs = toRoleViewPage(roles);
		rolesPage.setContent(roleRecordOutVOs);
	}

	private List<RoleRecordOutVO> toRoleViewPage(List<Role> roles) {
		List<RoleRecordOutVO> roleRecordOutVOs=new ArrayList<RoleRecordOutVO>();
		for(Role role:roles){
			roleRecordOutVOs.add(genRoleRecordOutVO(role));
		}
		return roleRecordOutVOs;
	} 
	
	private RoleRecordOutVO genRoleRecordOutVO(Role role){
		RoleRecordOutVO roleRecordOutVO=new RoleRecordOutVO();
		roleRecordOutVO.setId(role.getId());
		roleRecordOutVO.setRoleCode(role.getRoleCode());
		roleRecordOutVO.setRoleName(role.getRoleName());
		roleRecordOutVO.setDescription(role.getDescription());
		return roleRecordOutVO;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getGroupsByPage")
	public ResponseModel getRolesByPage(GroupSearchCriteria groupSearchCriteria,JSimplePageable simplePageable){
		JPage<Group> groupsPage= userManagerService.getGroupsByPage( groupSearchCriteria, simplePageable);
		toGroupViewPage(groupsPage);
		return ResponseModel.newSuccess().setData(groupsPage);
	}
	
	@ResponseBody
	@RequestMapping("/saveGroup")
	public ResponseModel saveGroup(GroupCreateInVO groupCreateInVO){
		Group group=JObjectUtils.simpleCopy(groupCreateInVO, Group.class);
		userManagerService.saveGroup( group);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/updateGroup")
	public ResponseModel updateGroup(GroupEditInVO groupEditInVO){
		Group group=JObjectUtils.simpleCopy(groupEditInVO, Group.class);
		userManagerService.updateGroup( group);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/deleteGroup")
	public ResponseModel deleteGroup(String id){
		userManagerService.deleteGroup( userManagerService.getGroupById( id));
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/getGroupById")
	public ResponseModel getGroupById(String id){
		Group group=userManagerService.getGroupById( id);
		GroupRecord groupRecordOutVO= genGroupRecordOutVO(group);
		return ResponseModel.newSuccess(groupRecordOutVO);
	}
	
	@ResponseBody
	@RequestMapping("/getRolesByGroupId")
	public ResponseModel getRolesByGroupId(String groupId){
		List<RoleRecord> roleRecords = userManagerService.getRolesByGroupId( groupId);
		List<RoleRecordOutVO> roleRecordOutVOs = toRoleRecordOutVO(roleRecords);
		return ResponseModel.newSuccess(roleRecordOutVOs);
	}
	
	@ResponseBody
	@RequestMapping("/getRolesByGroupIdByPage")
	public ResponseModel getRolesByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable){
		JPage<RoleRecord> page=userManagerService.getRolesByGroupIdByPage( groupId, simplePageable);
		page.setContent(toRoleRecordOutVO(page.getContent()));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbingRolesByGroupIdByPage")
	public ResponseModel getUnbingRolesByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable){
		List<RoleRecord> roleRecords=userManagerService.getUnbingRolesByGroupId( groupId);
		JPage<?> page=JPageImpl.wrap(toRoleRecordOutVO(roleRecords));
		return ResponseModel.newSuccess(page);
	}

	private List<RoleRecordOutVO> toRoleRecordOutVO(List<RoleRecord> roleRecords) {
		List<RoleRecordOutVO> roleRecordOutVOs=new ArrayList<RoleRecordOutVO>();
		for(int i=0;i<roleRecords.size();i++){
			RoleRecord roleRecord=roleRecords.get(i);
			roleRecordOutVOs.add(toRoleRecordOutVO(roleRecord));
		}
		return roleRecordOutVOs;
	}

	private RoleRecordOutVO toRoleRecordOutVO(RoleRecord roleRecord) {
		return JObjectUtils.simpleCopy(roleRecord, RoleRecordOutVO.class);
	}

//	private List<RoleRecordOutVO> rolesByGroupIdByPage(
//			 String groupId) {
//		List<RoleGroup> roleGroups=userManagerService.getRoleGroupsByGroupId( groupId);
//		List<RoleRecordOutVO> roleRecordOutVOs=new ArrayList<RoleRecordOutVO>();
//		for(RoleGroup roleGroup:roleGroups){
//			Role role=userManagerService.getRoleById( roleGroup.getRoleId());
//			if(role!=null){
//				roleRecordOutVOs.add(genRoleRecordOutVO(role));
//			}
//		}
//		return roleRecordOutVOs;
//	}
	
	
	private void toGroupViewPage(JPage<Group> groupsPage) {
		List<Group> roles=groupsPage.getContent();
		List<GroupRecord> groupRecordOutVOs = toGroupViewPage(roles);
		groupsPage.setContent(groupRecordOutVOs);
	}

	private List<GroupRecord> toGroupViewPage(List<Group> groups) {
		List<GroupRecord> groupRecordOutVOs=new ArrayList<GroupRecord>();
		for(Group group:groups){
			groupRecordOutVOs.add(genGroupRecordOutVO(group));
		}
		return groupRecordOutVOs;
	} 
	
	private GroupRecord genGroupRecordOutVO(Group group){
		GroupRecord groupRecordOutVO=new GroupRecord();
		groupRecordOutVO.setId(group.getId());
		groupRecordOutVO.setGroupCode(group.getGroupCode());
		groupRecordOutVO.setGroupName(group.getGroupName());
		groupRecordOutVO.setDescription(group.getDescription());
		return groupRecordOutVO;
	}
	
	@ResponseBody
	@RequestMapping("/bingRoleGroup")
	public ResponseModel bingRoleGroup(RoleGroupInVO roleGroupInVO){
		userManagerService.
				bingRoleGroup( roleGroupInVO.getRoleId(), roleGroupInVO.getGroupId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/unbingRoleGroup")
	public ResponseModel unbingRoleGroup(RoleGroupInVO roleGroupInVO){
		userManagerService.
				unbingRoleGroup( roleGroupInVO.getRoleId(), roleGroupInVO.getGroupId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/bingUserRole")
	public ResponseModel bingUserRole(UserRoleInVO userRoleInVO){
		userManagerService.
				bingUserRole( userRoleInVO.getUserId(), userRoleInVO.getRoleId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/unbingUserRole")
	public ResponseModel unbingUserRole(UserRoleInVO userRoleInVO){
		
		if(userManagerService.isDefaultRole( userRoleInVO.getRoleId())){
			throw new BusinessException("Default role cannot be removed.");
		}
		userManagerService.
				unbingUserRole( userRoleInVO.getUserId(), userRoleInVO.getRoleId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/bingUserGroup")
	public ResponseModel bingUserGroup(UserGroupInVO userGroupInVO){
		userManagerService.
		bingUserGroup( userGroupInVO.getUserId(), userGroupInVO.getGroupId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/unbingUserGroup")
	public ResponseModel unbingUserGroup(UserGroupInVO userGroupInVO){
		
		if(userManagerService.isDefaultGroup( userGroupInVO.getGroupId())){
			throw new BusinessException("Default group cannot be removed.");
		}
		
		userManagerService.
		unbingUserGroup( userGroupInVO.getUserId(), userGroupInVO.getGroupId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/getUsersByGroupIdByPage")
	public ResponseModel getUsersByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable) {
		JPage<UserRecord> page=userManagerService.getUsersByGroupIdByPage( groupId, simplePageable);
		page.setContent(toUserRecordOutVO(page.getContent()));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbingUsersByGroupIdByPage")
	public ResponseModel getUnbingUsersByGroupIdByPage(
			 String groupId,
			JSimplePageable simplePageable) {
		JPage<UserRecord> page=userManagerService.getUnbingUsersByGroupIdByPage( groupId, simplePageable);
		page.setContent(toUserRecordOutVO(page.getContent()));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUsersByGroupId")
	public ResponseModel getUsersByGroupId(
			 String groupId) {
		List<UserRecord> userRecords=userManagerService.getUsersByGroupId( groupId);
		return ResponseModel.newSuccess(toUserRecordOutVO(userRecords));
	}
	
	
	@ResponseBody
	@RequestMapping("/getUsersByRoleIdByPage")
	public ResponseModel getUsersByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		JPage<UserRecord> page=userManagerService.getUsersByRoleIdByPage( roleId, simplePageable);
		List<UserRecord> userRecords=page.getContent();
		page.setContent(toUserRecordOutVO(userRecords));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbingUsersByRoleIdByPage")
	public ResponseModel getUnbingUsersByRoleIdByPage(
			 String roleId,
			JSimplePageable simplePageable) {
		JPage<UserRecord> page=userManagerService.getUnbingUsersByRoleIdByPage( roleId, simplePageable);
		List<UserRecord> userRecords=page.getContent();
		page.setContent(toUserRecordOutVO(userRecords));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUsersByRoleId")
	public ResponseModel getUsersByRoleId(
			 String roleId) {
		List<UserRecord> userRecords=userManagerService.getUsersByRoleId( roleId);
		return ResponseModel.newSuccess(toUserRecordOutVO(userRecords));
	}

	private List<UserRecordOutVO> toUserRecordOutVO(List<UserRecord> userRecords) {
		List<UserRecordOutVO> userRecordOutVOs=new ArrayList<UserRecordOutVO>();
		for(int i=0;i<userRecords.size();i++){
			UserRecord userRecord=userRecords.get(i);
			userRecordOutVOs.add(toUserRecordOutVO(userRecord));
		}
		return userRecordOutVOs;
	}

	private UserRecordOutVO toUserRecordOutVO(UserRecord userRecord) {
		return JObjectUtils.simpleCopy(userRecord, UserRecordOutVO.class);
	}
	
	
	@ResponseBody
	@RequestMapping("/getRolesByUserIdByPage")
	public ResponseModel getRolesByUserIdByPage(
			 String userId,
			JSimplePageable simplePageable){
		List<RoleRecord> roleRecords=userManagerService.getRolesByUserId( userId);
		JPage<?> page=JPageImpl.wrap(toRoleRecordOutVO(roleRecords));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbingRolesByUserIdByPage")
	public ResponseModel getUnbingRolesByUserIdByPage(
			 String userId,
			JSimplePageable simplePageable){
		List<RoleRecord> roleRecords=userManagerService.getUnbingRolesByUserId( userId);
		JPage<?> page=JPageImpl.wrap(toRoleRecordOutVO(roleRecords));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getGroupsByUserIdByPage")
	public ResponseModel getGroupsByUserIdByPage(
			 String userId,
			JSimplePageable simplePageable){
		List<GroupRecord> groupRecords=userManagerService.getGroupsByUserId( userId);
		JPage<?> page=JPageImpl.wrap(toGroupRecordOutVO(groupRecords));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbingGroupsByUserIdByPage")
	public ResponseModel getUnbingGroupsByUserIdByPage(
			 String userId,
			JSimplePageable simplePageable){
		List<GroupRecord> groupRecords=userManagerService.getUnbingGroupsByUserId( userId);
		JPage<?> page=JPageImpl.wrap(toGroupRecordOutVO(groupRecords));
		return ResponseModel.newSuccess(page);
	}
	
}
