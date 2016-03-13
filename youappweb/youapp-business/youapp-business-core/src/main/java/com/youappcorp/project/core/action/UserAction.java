package com.youappcorp.project.core.action;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.platform.basicwebcomp.web.youappmvc.jspview.JSPAction;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller(value="user.useraction")
@RequestMapping(value="/userManager")
public class UserAction extends JSPAction{
	
	@RequestMapping(value="/saveUser")
	public void saveUser(JConfiguration configuration,HashMap<String, Object> map,JPageRequest pageRequest){
		System.out.println("ok! scu...");
	}
	
	
	
	
	
}
