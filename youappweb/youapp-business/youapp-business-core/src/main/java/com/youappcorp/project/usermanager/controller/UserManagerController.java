package com.youappcorp.project.usermanager.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.ControllerSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserSearchCriteria;
import com.youappcorp.project.usermanager.service.UserManagerService;
import com.youappcorp.project.usermanager.service.UserService;

@Controller
@RequestMapping(value="/usermanager")
public class UserManagerController extends ControllerSupport {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserManagerService userManagerService;
	
	@RequestMapping(value="/getUsersByPage")
	public ResponseModel getUsersByPage(UserSearchCriteria userSearchCriteria){
		JPage<User> users= userService.getUsersByPage(getServiceContext(), userSearchCriteria);
		return ResponseModel.newSuccess().setData(users);
	}
	
	
	
}
