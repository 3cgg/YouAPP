package j.jave.framework.components.login.action;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.model.UserTracker;
import j.jave.framework.components.login.service.LoginAccessService;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.login.service.UserTrackerService;
import j.jave.framework.components.login.view.TimeLineGroup;
import j.jave.framework.components.login.view.TimelineView;
import j.jave.framework.components.memcached.JMemcachedDistService;
import j.jave.framework.components.views.HTTPAction;
import j.jave.framework.components.views.HTTPContext;
import j.jave.framework.components.weight.model.Weight;
import j.jave.framework.components.weight.service.WeightService;
import j.jave.framework.security.APPCipher;
import j.jave.framework.utils.JUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



@Controller(value="login.loginaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginAction extends HTTPAction {
	
	private User user;
	
	@Autowired
	private LoginAccessService loginAccessService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JMemcachedDistService jMemcachedDistService;
	
	@Autowired
	private UserTrackerService userTrackerService;
	
	@Autowired
	private WeightService weightService;
	
	/**
	 * the unique entrance to APP. 
	 * @return
	 */
	public String index() throws Exception{
		getTimeline(); 
		List<Weight> weights=weightService.getWeightByName(getServiceContext(), getSessionUser().getUserName());
		setAttribute("weights", weights);
		return "/WEB-INF/jsp/index.jsp";
	}

	private void getTimeline() {
		List<UserTracker> userTrackers= userTrackerService.getUserTrackerByName(getSessionUser().getUserName());
		List<TimeLineGroup> timeLineGroups=new ArrayList<TimeLineGroup>();
		if(userTrackers!=null){
			String doDate="";
			TimeLineGroup doTimeLineGroup=null;
			for (Iterator<UserTracker> iterator = userTrackers.iterator(); iterator
					.hasNext();) {
				UserTracker userTracker = (UserTracker) iterator.next();
				String date=JUtils.format(userTracker.getLoginTime());
				if(!date.equals(doDate)){
					// new group 
					doTimeLineGroup=new TimeLineGroup();
					doTimeLineGroup.setDate(date);
					doDate=date;
					timeLineGroups.add(doTimeLineGroup);
				}
				
				TimelineView timelineView=new TimelineView();
				// fillin highlight 
				timelineView.setHighlightContent(userTracker.getIp());
				timelineView.setHighlightPath("/login.loginaction/profile");
				// fillin header 
				String header=userTracker.getLoginClient();
				timelineView.setHeader(header);
				//fillin timeoffset
				timelineView.setTimeOffset(JUtils.getTimeOffset(userTracker.getLoginTime()));
				doTimeLineGroup.getTimelineViews().add(timelineView);
			}
		}
		setAttribute("timelineGroups", timeLineGroups);
	}

	private void logTracker() throws ServiceException {
		UserTracker userTracker=new UserTracker();
		User sessionUser=getSessionUser();
		userTracker.setUserId(sessionUser.getId());
		userTracker.setUserName(sessionUser.getUserName());
		userTracker.setIp(httpContext.getIP());
		userTracker.setLoginClient(httpContext.getClient());
		userTrackerService.saveUserTracker(getServiceContext(), userTracker);
	}
	
	public String toLogin(){
		return "/WEB-INF/jsp/login/login.jsp";
	}
	
	
	public String login() throws Exception {
		String username=user.getUserName(); 
		String password=user.getPassword();
		String ticket=loginAccessService.login(username, password);
		loginLogic(userService.getUserByName(getServiceContext(), user.getUserName()), ticket);
		logTracker();
		return navigate("/login.loginaction/index");
	}


	private void loginLogic(User loginUser, String unique) {
		String uniqueKey="ticket-"+unique;
		HTTPContext httpContext=new HTTPContext();
		User user=new User();
		user.setUserName(loginUser.getUserName());
		user.setId(loginUser.getId()); 
		httpContext.setUser(user);
		httpContext.setTicket(uniqueKey);
		jMemcachedDistService.set(uniqueKey, 0, httpContext);
		
		this.httpContext.setUser(loginUser); 
		
		setCookie("ticket", uniqueKey,-1);
	}
	
	public String profile(){
		User sessionUser=getSessionUser();
		User dbUser=userService.getUserByName(null, sessionUser.getUserName());
		setAttribute("user", dbUser);
		return "/WEB-INF/jsp/login/view-user.jsp";
	}
	
	public String toCreateUser(){
		return "/WEB-INF/jsp/login/create-user.jsp";
	}
	
	public String createUser() throws Exception {
		String passwrod=user.getPassword();
		String encriptPassword=APPCipher.get().encrypt(passwrod);
		user.setPassword(encriptPassword);
		ServiceContext context=new ServiceContext();
		context.setUser(getSessionUser());
		userService.saveUser(context, user);
		setAttribute("user", user);
		return "/WEB-INF/jsp/login/view-user.jsp";
	}
	
	public String toRegister(){
		return "/WEB-INF/jsp/login/register.jsp";
	}
	
	public String register() throws Exception {
		
		ServiceContext context=new ServiceContext();
		context.setUser(getSessionUser());
		loginAccessService.register(context, user);
		loginLogic(user, JUtils.unique()+"-"+user.getUserName());
		setAttribute("user", user);
		logTracker();
		return navigate( "/login.loginaction/index");
	}
	
	public String loginout() throws Exception  {
		String ticket=httpContext.getTicket();
		jMemcachedDistService.delete(ticket);
		deleteCookie("ticket"); 
		return navigate("/login.loginaction/toLogin");
	}
	
	public String tracker(){
		getTimeline();
		return "/WEB-INF/jsp/login/tracker.jsp";
	}
	
}
