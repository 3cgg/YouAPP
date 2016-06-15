package com.youappcorp.project.websupport.service;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.webcomp.access.subhub.AuthenticationManagerService;
import j.jave.platform.webcomp.access.subhub.AuthorizedResource;
import j.jave.platform.webcomp.core.service.DefaultServiceContext;
import j.jave.platform.webcomp.core.service.SessionUserImpl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.service.UserService;

@Service(value="com.youappcorp.project.websupport.service.AuthenticationManagerServiceImpl")
public class AuthenticationManagerServiceImpl extends SpringServiceFactorySupport<AuthenticationManagerService> implements
		AuthenticationManagerService {

	@Autowired
	private UserService userService;
	
	@Override
	public SessionUserImpl getUserByNameAndPassword(String name, String password) {
		User user=userService.getUserByNameAndPassword(name,password);
		SessionUserImpl sessionUserImpl=null;
		if(user!=null){
			sessionUserImpl =new SessionUserImpl();
			sessionUserImpl.setUserId(user.getId());
			sessionUserImpl.setUserName(user.getUserName());
		}
		return sessionUserImpl;
	}

	@Override
	public SessionUserImpl getUserByName(String name) {
		User user=userService.getUserByName(DefaultServiceContext.getDefaultServiceContext(), name);
		SessionUserImpl sessionUserImpl=null;
		if(user!=null){
			sessionUserImpl =new SessionUserImpl();
			sessionUserImpl.setUserId(user.getId());
			sessionUserImpl.setUserName(user.getUserName());
		}
		return sessionUserImpl;
	}

	@Override
	public List<AuthorizedResource> getAllAuthorizedResources() {
		return Collections.EMPTY_LIST;
	}

}
