package j.jave.framework.components.login.action.mobile;

import j.jave.framework.components.core.model.SessionUserInfo;
import j.jave.framework.components.core.support.AndroidActionSupport;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.model.UserSearchCriteria;
import j.jave.framework.components.login.model.UserTracker;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.login.service.UserTrackerService;
import j.jave.framework.components.support.memcached.subhub.MemcachedService;
import j.jave.framework.components.web.mobile.MobileResult;
import j.jave.framework.components.web.model.JHttpContext;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.components.web.subhub.sessionuser.SessionUser;
import j.jave.framework.components.web.utils.JHttpUtils;
import j.jave.framework.servicehub.JServiceHubDelegate;
import j.jave.framework.servicehub.exception.JServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value="mobile.login.loginaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginMobileAction extends AndroidActionSupport {

	private User user;
	
	private UserSearchCriteria userSearchCriteria;
	
	@Autowired
	private LoginAccessService loginAccessService;
	
	@Autowired
	private UserService userService;
	
	private MemcachedService jMemcachedDistService=
			JServiceHubDelegate.get().getService(this,MemcachedService.class);
	
	@Autowired
	private UserTrackerService userTrackerService;
	
	public MobileResult login() throws JServiceException{
		MobileResult mobileResult=new MobileResult();
		String username=user.getUserName(); 
		String password=user.getPassword();
		String ticket=loginAccessService.login(username, password);
		User dbUser=userService.getUserByName(getServiceContext(), user.getUserName());
		loginLogic(dbUser, ticket);
		logTracker();
		mobileResult.setData(dbUser);
		return mobileResult;
	} 
	
	private void loginLogic(User loginUser, String unique) {
		String uniqueKey="ticket-"+unique;
		JHttpContext httpContext=new JHttpContext();
		SessionUserInfo sessionUserInfo=new SessionUserInfo();
		sessionUserInfo.setUserName(loginUser.getUserName());
		sessionUserInfo.setId(loginUser.getId()); 
		httpContext.setUser(sessionUserInfo);
		httpContext.setTicket(uniqueKey);
		jMemcachedDistService.set(uniqueKey, 0, httpContext);
		this.httpContext.setUser(sessionUserInfo); 
		//setCookie("ticket", uniqueKey,-1); invalid for mobile app. 
	}
	
	private void logTracker() throws JServiceException {
		UserTracker userTracker=new UserTracker();
		SessionUser sessionUser=getSessionUser();
		userTracker.setUserId(sessionUser.getId());
		userTracker.setUserName(sessionUser.getUserName());
		userTracker.setIp(httpContext.getIP());
		userTracker.setLoginClient(httpContext.getClient());
		userTrackerService.saveUserTracker(getServiceContext(), userTracker);
	}
	
	public MobileResult loginout() throws Exception  {
		MobileResult mobileResult=new MobileResult();
		String ticket=JHttpUtils.getTicket(httpContext.getRequest());
		jMemcachedDistService.delete(ticket);
		//deleteCookie("ticket");  invalid for mobile app.  
		return mobileResult;
	}
	
	
}
