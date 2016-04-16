package com.youappcorp.project.usermanager.service;

import j.jave.platform.basicsupportcomp.core.servicehub.SkipServiceNameCheck;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.model.User;

@Service(value="userManagerServiceImpl.transation.jpa")
@SkipServiceNameCheck
public class UserManagerServiceImpl
extends SpringServiceFactorySupport<UserManagerService>
implements UserManagerService {

	@Autowired
	private UserService userService;
	
	
	@Override
	public User getUserByName(ServiceContext context, String userName) {
		return userService.getUserByName(context, userName);
	}

}
