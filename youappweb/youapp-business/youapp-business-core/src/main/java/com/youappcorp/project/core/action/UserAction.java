package com.youappcorp.project.core.action;

import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.web.youappmvc.jspview.JSPAction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller(value="bill.billaction")
@RequestMapping(value="/userManager")
public class UserAction extends JSPAction{
	
	@RequestMapping(value="/saveUser")
	public void saveUser(User user){
		
		System.out.println(user);
		
	}
	
	
	
	
	
}
