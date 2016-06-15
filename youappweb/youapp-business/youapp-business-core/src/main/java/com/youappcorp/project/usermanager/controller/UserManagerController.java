package com.youappcorp.project.usermanager.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserSearchCriteria;
import com.youappcorp.project.usermanager.model.UserTracker;
import com.youappcorp.project.usermanager.service.UserManagerService;
import com.youappcorp.project.usermanager.service.UserService;
import com.youappcorp.project.usermanager.service.UserTrackerService;
import com.youappcorp.project.usermanager.vo.TimeLineGroup;
import com.youappcorp.project.usermanager.vo.TimelineView;

@Controller
@RequestMapping(value="/usermanager")
public class UserManagerController extends ControllerSupport {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserTrackerService userTrackerService;
	
	@Autowired
	private UserManagerService userManagerService;
	
	@ResponseBody
	@RequestMapping(value="/getUsersByPage")
	public ResponseModel getUsersByPage(UserSearchCriteria userSearchCriteria){
		JPage<User> users= userService.getUsersByPage(getServiceContext(), userSearchCriteria);
		return ResponseModel.newSuccess().setData(users);
	}
	
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
	@RequestMapping(value="/getUserById")
	public ResponseModel getUserById(String id) throws Exception {
		User user= userService.getUserById(getServiceContext(), id);
		return ResponseModel.newSuccess().setData(user);
	}
	
}
