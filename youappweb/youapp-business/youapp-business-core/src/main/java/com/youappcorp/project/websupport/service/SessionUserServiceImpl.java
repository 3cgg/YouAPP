package com.youappcorp.project.websupport.service;

import j.jave.platform.webcomp.access.subhub.SessionUserGetEvent;
import j.jave.platform.webcomp.access.subhub.SessionUserService;
import j.jave.platform.webcomp.access.subhub.SessionUserServiceFactory;
import j.jave.platform.webcomp.core.service.DefaultSessionUser;
import j.jave.platform.webcomp.core.service.SessionUser;

import org.springframework.stereotype.Service;

@Service(value="SessionUserServiceImpl")
public class SessionUserServiceImpl extends SessionUserServiceFactory implements SessionUserService{

	public SessionUserServiceImpl() {
		super(SessionUserService.class);
	}
	
	@Override
	protected SessionUserService doGetService() {
		return getBeanByName(SessionUserServiceImpl.class.getName());
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
