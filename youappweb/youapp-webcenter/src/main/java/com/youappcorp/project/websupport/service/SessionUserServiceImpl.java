package com.youappcorp.project.websupport.service;

import org.springframework.stereotype.Service;

import me.bunny.app._c._web.access.subhub.SessionUserGetEvent;
import me.bunny.app._c._web.access.subhub.SessionUserService;
import me.bunny.app._c._web.access.subhub.SessionUserServiceFactory;
import me.bunny.app._c._web.core.service.DefaultSessionUser;
import me.bunny.app._c._web.core.service.SessionUser;

@Service(SessionUserServiceImpl.BEAN_NAME)
public class SessionUserServiceImpl extends SessionUserServiceFactory implements SessionUserService{

	public static final String BEAN_NAME="default-sessionUserServiceImpl";
	
	public SessionUserServiceImpl() {
		super(SessionUserService.class);
	}
	
	@Override
	protected SessionUserService doGetService() {
		return getBeanByName(SessionUserServiceImpl.BEAN_NAME);
	}

	@Override
	public SessionUser trigger(SessionUserGetEvent event) {
		return DefaultSessionUser.getDefaultSessionUser();
	}

	@Override
	public SessionUser newSessionUser() {
		return DefaultSessionUser.getDefaultSessionUser();
	}

}
