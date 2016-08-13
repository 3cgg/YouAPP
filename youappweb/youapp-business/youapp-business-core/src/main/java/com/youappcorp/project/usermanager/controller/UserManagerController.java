package com.youappcorp.project.usermanager.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.Role;
import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserExtend;
import com.youappcorp.project.usermanager.model.UserTracker;
import com.youappcorp.project.usermanager.service.DefaultUserManagerServiceImpl;
import com.youappcorp.project.usermanager.service.UserTrackerService;
import com.youappcorp.project.usermanager.vo.ResetPasswordVO;
import com.youappcorp.project.usermanager.vo.RoleCreateInVO;
import com.youappcorp.project.usermanager.vo.RoleEditInVO;
import com.youappcorp.project.usermanager.vo.TimeLineGroup;
import com.youappcorp.project.usermanager.vo.TimelineView;
import com.youappcorp.project.usermanager.vo.UserRegisterInVO;
import com.youappcorp.project.usermanager.vo.UserSearchCriteria;

@Controller
@RequestMapping(value="/usermanager")
public class UserManagerController extends SimpleControllerSupport {
	
	@Autowired
	private UserTrackerService userTrackerService;
	
	@Autowired
	private DefaultUserManagerServiceImpl userManagerService;
	
	@ResponseBody
	@RequestMapping(value="/getUsersByPage")
	public ResponseModel getUsersByPage(ServiceContext serviceContext,UserSearchCriteria userSearchCriteria,JSimplePageable simplePageable){
		JPage<User> users= userManagerService.getUsersByPage(serviceContext, userSearchCriteria,simplePageable);
		return ResponseModel.newSuccess().setData(users);
	}
	
	@ResponseBody
	@RequestMapping(value="/getTimeline")
	public ResponseModel getTimeline(ServiceContext serviceContext,
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
	@RequestMapping(value="/getUserById")
	public ResponseModel getUserById(ServiceContext serviceContext,String id) throws Exception {
		User user= userManagerService.getUserById(serviceContext, id);
		return ResponseModel.newSuccess().setData(user);
	}
	
	@ResponseBody
	@RequestMapping(value="/register")
	public ResponseModel register(ServiceContext context,UserRegisterInVO userRegisterInVO){
		
		if(!userRegisterInVO.getPassword().equals(userRegisterInVO.getRetypePassword())){
			throw new BusinessException("两次输入的密码不一样");
		}
		
		User user=new User();
		user.setPassword(userRegisterInVO.getPassword());
		user.setUserName(userRegisterInVO.getUserName());
		UserExtend userExtend=new UserExtend();
		userExtend.setNatureName(userRegisterInVO.getNatureName());
		userExtend.setUserName(userRegisterInVO.getUserName());
		userManagerService.register(context, user, userExtend);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value="/resetPassword")
	public ResponseModel resetPassword(ServiceContext context, ResetPasswordVO resetPasswordVO){
		
		if(JStringUtils.isNullOrEmpty(resetPasswordVO.getPassword())){
			throw new BusinessException("密码不能为空");
		}
		
		userManagerService.resetPassword(context, resetPasswordVO.getUserId(), resetPasswordVO.getPassword());
		return ResponseModel.newSuccess();
	}
	
	@RequestMapping("/saveRole")
	public ResponseModel saveRole(ServiceContext context,RoleCreateInVO roleCreateInVO){
		Role role=JObjectUtils.simpleCopy(roleCreateInVO, Role.class);
		userManagerService.saveRole(context, role);
		return ResponseModel.newSuccess();
	}
	
	@RequestMapping("/updateRole")
	public ResponseModel updateRole(ServiceContext context,RoleEditInVO roleEditInVO){
		Role role=JObjectUtils.simpleCopy(roleEditInVO, Role.class);
		userManagerService.updateRole(context, role);
		return ResponseModel.newSuccess();
	}
	
	@RequestMapping("/deleteRole")
	public ResponseModel deleteRole(ServiceContext context,String id){
		Role role=JObjectUtils.simpleCopy(userManagerService.getRoleById(context, id), Role.class);
		userManagerService.deleteRole(context, role);
		return ResponseModel.newSuccess();
	}
}
