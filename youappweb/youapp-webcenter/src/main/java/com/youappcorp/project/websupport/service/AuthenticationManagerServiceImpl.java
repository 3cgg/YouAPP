package com.youappcorp.project.websupport.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.sps.support.security.subhub.DESedeCipherService;
import j.jave.platform.webcomp.access.subhub.AuthenticationManagerService;
import j.jave.platform.webcomp.access.subhub.AuthorizedResource;
import j.jave.platform.webcomp.core.service.SessionUserImpl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.service.DefaultUserManagerServiceImpl;

@Service(value="AuthenticationManagerServiceImpl")
public class AuthenticationManagerServiceImpl extends SpringServiceFactorySupport<AuthenticationManagerService> implements
		AuthenticationManagerService {

	@Autowired
	private DefaultUserManagerServiceImpl userManagerService;
	
	private DESedeCipherService deSedeCipherService=
			JServiceHubDelegate.get().getService(this, DESedeCipherService.class);
	
	@Override
	public SessionUserImpl getUserByNameAndPassword(String name, String password) {
		String encryptPassword=null;
		try {
			encryptPassword =deSedeCipherService.encrypt(password.trim());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		User user=userManagerService.getUserByNameAndPassword(name,encryptPassword);
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
		User user=userManagerService.getUserByName( name);
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
