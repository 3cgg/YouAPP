package j.jave.framework.components.login.action.jsp;

import j.jave.framework.components.core.model.JQueryDataTablePage;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.servicehub.ServiceHubDelegate;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.components.login.model.Role;
import j.jave.framework.components.login.model.RoleSearchCriteria;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.model.UserGroup;
import j.jave.framework.components.login.model.UserRole;
import j.jave.framework.components.login.model.UserSearchCriteria;
import j.jave.framework.components.login.model.UserTracker;
import j.jave.framework.components.login.service.GroupService;
import j.jave.framework.components.login.service.RoleService;
import j.jave.framework.components.login.service.UserGroupService;
import j.jave.framework.components.login.service.UserRoleService;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.login.service.UserTrackerService;
import j.jave.framework.components.login.subhub.LoginAccessService;
import j.jave.framework.components.login.view.TimeLineGroup;
import j.jave.framework.components.login.view.TimelineView;
import j.jave.framework.components.memory.ResourceCachedRefreshEvent;
import j.jave.framework.components.support.memcached.subhub.MemcachedService;
import j.jave.framework.components.web.action.HTTPContext;
import j.jave.framework.components.web.jsp.JSPAction;
import j.jave.framework.exception.JOperationNotSupportedException;
import j.jave.framework.json.JJSON;
import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.support.security.JAPPCipher;
import j.jave.framework.utils.JDateUtils;
import j.jave.framework.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



@Controller(value="login.loginaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginJSPAction extends JSPAction {

	private static final long serialVersionUID = 4299587930782979586L;

	private User user;
	
	private UserSearchCriteria userSearchCriteria;
	
	private RoleSearchCriteria roleSearchCriteria;
	
	@Autowired
	private LoginAccessService loginAccessService;
	
	@Autowired
	private UserService userService;
	
	private MemcachedService jMemcachedDistService=
			ServiceHubDelegate.get().getService(this,MemcachedService.class);
	
	@Autowired
	private UserTrackerService userTrackerService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	/**
	 * the unique entrance to APP. 
	 * @return
	 */
	public String index() throws Exception{
		getTimeline(); 
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
				String date=JDateUtils.format(userTracker.getLoginTime());
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
				timelineView.setTimeOffset(JDateUtils.getTimeOffset(userTracker.getLoginTime()));
				doTimeLineGroup.getTimelineViews().add(timelineView);
			}
		}
		setAttribute("timelineGroups", timeLineGroups);
	}

	private void logTracker() throws JServiceException {
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
		String encriptPassword=JAPPCipher.get().encrypt(passwrod);
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
		loginLogic(user, JUniqueUtils.unique()+"-"+user.getUserName());
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
	
	public String toViewAllUser() throws Exception{
		return getUsersWithsCondition();
	}
	
	public String getUsersWithsCondition(){
		if(userSearchCriteria==null){
			userSearchCriteria=new UserSearchCriteria();
		}
		List<User> users=userService.getUsersByPage(getServiceContext(), userSearchCriteria);
		setAttribute("users", users);
		setAttribute("userSearchCriteria", userSearchCriteria);
		return "/WEB-INF/jsp/login/view-all-user.jsp";
	}
	
	
	public String deleteUser(){
//		userService.delete(getServiceContext(), getParameter("id")); 
//		setSuccessMessage(DELETE_SUCCESS);
//		return getUsersWithsCondition();
		throw new JOperationNotSupportedException("User Delete Not Supported!");
	}
	
	public String toViewUser() throws Exception {
		
		String id=getParameter("id");
		User user= userService.getUserById(getServiceContext(), id);
		if(user!=null){
			setAttribute("user", user);
		}
		return "/WEB-INF/jsp/login/view-user.jsp"; 
	}
	
	
	public String toUploadImage(){
		User sessionUser=getSessionUser();
		User dbUser=userService.getUserByName(null, sessionUser.getUserName());
		setAttribute("user", dbUser);
		return "/WEB-INF/jsp/login/image-user.jsp";
	}
	
	public String uploadImage(){
		User sessionUser=getSessionUser();
		User dbUser=userService.getUserByName(null, sessionUser.getUserName());
		setAttribute("user", dbUser);
		setSuccessMessage(EDIT_SUCCESS);
		return "/WEB-INF/jsp/login/image-user.jsp";
	} 
	
	
	public String toNavigate(){
		return "/WEB-INF/jsp/login/navigate-login.jsp";
	}
	
	public String toUserAuthorized(){
		List<Role> roles =roleService.getAllRoles(getServiceContext());
		setAttribute("roles", roles);
		
		List<Group> groups =groupService.getAllGroups(getServiceContext());
		setAttribute("groups", groups);
//		return navigate("/login.loginaction/testtoUserAuthorized");
//		return getTestPage("/WEB-INF/jsp/login/user-authorized.jsp");
		return "/WEB-INF/jsp/login/user-authorized.jsp";
	}
	
	
	public String getAllUsers(){
		
		ServiceHubDelegate.get().addDelayEvent(new ResourceCachedRefreshEvent(this, JAPPEvent.HIGEST));

		JQueryDataTablePage page=parseJPage();
		userSearchCriteria.setPage(page);
		List<User> users=userService.getUsersByPage(getServiceContext(), userSearchCriteria);
		page.setAaData(users);
		return JJSON.get().format(page); 
		
	}
	
	
	
	public String getAllRoles(){
		
		JQueryDataTablePage page=parseJPage();
		roleSearchCriteria.setPage(page);
		List<Role> roles=roleService.getRoleByRoleNameByPage(getServiceContext(), roleSearchCriteria);
		page.setAaData(roles);
		return JJSON.get().format(page); 
	}
	
	
	public String getUserRole(){
		String userId=getParameter("userId");
		List<UserRole> userRoles= userRoleService.getUserRolesByUserId(getServiceContext(), userId);
		return JJSON.get().format(userRoles); 
	}
	
	
	public String bingUserOnRole() throws Exception{
		String userId=getParameter("userId").trim();
		String roleId=getParameter("roleId").trim();
		userRoleService.bingUserRole(getServiceContext(), userId, roleId);
		
		List<UserRole> userRoles= userRoleService.getUserRolesByUserId(getServiceContext(), userId);
		return JJSON.get().format(userRoles); 
	}
	
	public String unbingUserOnRole() throws Exception{
		String userId=getParameter("userId").trim();
		String roleId=getParameter("roleId").trim();
		userRoleService.unbingUserRole(getServiceContext(), userId, roleId);
		List<UserRole> userRoles= userRoleService.getUserRolesByUserId(getServiceContext(), userId);
		return JJSON.get().format(userRoles); 
		
	}
	
	public String getUserGroup(){
		String userId=getParameter("userId");
		List<UserGroup> userRoles= userGroupService.getUserGroupsByUserId(getServiceContext(), userId);
		return JJSON.get().format(userRoles); 
	}
	
	public String bingUserOnGroup() throws Exception{
		String userId=getParameter("userId").trim();
		String groupId=getParameter("groupId").trim();
		userGroupService.bingUserGroup(getServiceContext(), userId, groupId);

		List<UserGroup> userGroups= userGroupService.getUserGroupsByUserId(getServiceContext(), userId);
		return JJSON.get().format(userGroups); 
	}
	
	public String unbingUserOnGroup() throws Exception{
		String userId=getParameter("userId").trim();
		String groupId=getParameter("groupId").trim();
		userGroupService.unbingUserGroup(getServiceContext(), userId, groupId);
		List<UserGroup> userGroups= userGroupService.getUserGroupsByUserId(getServiceContext(), userId);
		return JJSON.get().format(userGroups); 
		
	}
	
	
	
	
}
