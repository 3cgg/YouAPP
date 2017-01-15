package com.youappcorp.project.websupport.service;

import me.bunny.app._c._web.access.subhub.AuthenticationManagerService;
import me.bunny.app._c._web.access.subhub.AuthorizedResource;
import me.bunny.app._c._web.core.service.SessionUserImpl;
import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.app._c.sps.support.security.subhub.DESedeCipherService;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

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
