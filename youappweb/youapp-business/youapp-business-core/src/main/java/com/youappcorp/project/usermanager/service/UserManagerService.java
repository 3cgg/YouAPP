package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.webcomp.core.service.ServiceContext;

import com.youappcorp.project.usermanager.model.User;


public interface UserManagerService extends JService{

	/**
	 * get user by name 
	 * @param context
	 * @param userName
	 * @return
	 */
	public User getUserByName(ServiceContext context, String userName);

	
}
