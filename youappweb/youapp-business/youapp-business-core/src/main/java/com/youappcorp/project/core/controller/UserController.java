package com.youappcorp.project.core.controller;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.action.ControllerSupport;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller(value="user.usercontroller")
@RequestMapping(value="/userManager")
public class UserController extends ControllerSupport{
	
	@RequestMapping(value="/saveUser")
	public ResponseModel saveUser(JConfiguration configuration,HashMap<String, Object> map,JPageRequest pageRequest){
		System.out.println("ok! scu...");
		return ResponseModel.newSuccess().setData("ok! scu...");
	}
	
	
	
	
	
}
