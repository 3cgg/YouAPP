package com.youappcorp.project.websupport.service;

import j.jave.platform.webcomp.access.subhub.AuthenticationHookService;
import j.jave.platform.webcomp.core.service.SessionUser;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.model.UserTracker;
import com.youappcorp.project.usermanager.service.UserTrackerService;

@Service(value="com.youappcorp.project.websupport.service.AuthenticationHookServiceImpl")
public class AuthenticationHookServiceImpl implements AuthenticationHookService {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(AuthenticationHookServiceImpl.class);
	
	@Autowired
	private UserTrackerService userTrackerService;
	
	@Override
	public void doAfterLogin(HttpContext httpContext) {
		try{
			UserTracker userTracker=new UserTracker();
			SessionUser sessionUser=httpContext.getUser();
			userTracker.setUserId(sessionUser.getUserId());
			userTracker.setUserName(sessionUser.getUserName());
			userTracker.setIp(httpContext.getClientInfo().getIp());
			userTracker.setLoginClient(httpContext.getClientInfo().getClient());
			userTrackerService.saveUserTracker(userTracker);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}
	
	@Override
	public void doAfterLoginout(HttpContext httpContext) {
		LOGGER.info("loginout..... ----> "+httpContext.getServiceContext().getTicket());
	}
}
