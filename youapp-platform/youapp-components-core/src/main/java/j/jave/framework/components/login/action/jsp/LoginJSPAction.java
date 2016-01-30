package j.jave.framework.components.login.action.jsp;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.exception.JOperationNotSupportedException;
import j.jave.framework.commons.json.JJSON;
import j.jave.framework.commons.qrcode.JQRCode;
import j.jave.framework.commons.utils.JDateUtils;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.commons.utils.JUniqueUtils;
import j.jave.framework.components.core.model.SessionUserInfo;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.support.JSPActionSupport;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.components.login.model.Role;
import j.jave.framework.components.login.model.RoleGroup;
import j.jave.framework.components.login.model.RoleSearchCriteria;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.model.UserGroup;
import j.jave.framework.components.login.model.UserRole;
import j.jave.framework.components.login.model.UserSearchCriteria;
import j.jave.framework.components.login.model.UserTracker;
import j.jave.framework.components.login.service.GroupService;
import j.jave.framework.components.login.service.RoleGroupService;
import j.jave.framework.components.login.service.RoleService;
import j.jave.framework.components.login.service.UserGroupService;
import j.jave.framework.components.login.service.UserRoleService;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.login.service.UserTrackerService;
import j.jave.framework.components.login.view.TimeLineGroup;
import j.jave.framework.components.login.view.TimelineView;
import j.jave.framework.components.support.memcached.subhub.MemcachedService;
import j.jave.framework.components.web.model.JHttpContext;
import j.jave.framework.components.web.model.JQueryDataTablePage;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCachedRefreshEvent;
import j.jave.framework.components.web.subhub.sessionuser.SessionUser;
import j.jave.framework.components.web.utils.JHttpUtils;
import j.jave.framework.inner.support.rs.JRSSecurityHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



@Controller(value="login.loginaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginJSPAction extends JSPActionSupport {

	private static final long serialVersionUID = 4299587930782979586L;

	private User user;
	
	private UserSearchCriteria userSearchCriteria;
	
	private RoleSearchCriteria roleSearchCriteria;
	
	@Autowired
	private LoginAccessService loginAccessService;
	
	@Autowired
	private UserService userService;
	
	private MemcachedService jMemcachedDistService=
			JServiceHubDelegate.get().getService(this,MemcachedService.class);
	
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
	
	@Autowired
	private RoleGroupService roleGroupService;
	
	private Role role;
	
	private Group group;
	
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
		SessionUser sessionUser=getSessionUser();
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
		JHttpContext httpContext=new JHttpContext();
		SessionUserInfo sessionUserInfo=new SessionUserInfo();
		sessionUserInfo.setUserName(loginUser.getUserName());
		sessionUserInfo.setId(loginUser.getId()); 
		httpContext.setUser(sessionUserInfo);
		httpContext.setTicket(unique);
		jMemcachedDistService.set(unique, 0, httpContext);
		
		this.httpContext.setUser(sessionUserInfo); 
		String remember=getParameter("remember");
		
		//remember case
		if(JStringUtils.isNotNullOrEmpty(remember)&&("on".equals(remember)||"true".equals(remember))){
			setCookie("ticket", unique, 60*60*24*7);
		}
		else{
			setCookie("ticket", unique,-1);
		}
		
	}
	
	public String profile(){
		SessionUser sessionUser=getSessionUser();
		User dbUser=userService.getUserByName(null, sessionUser.getUserName());
		setAttribute("user", dbUser);
		byte[] bytes = getQRCode(dbUser);
		setAttribute("qrcode", JStringUtils.bytestoBASE64String(bytes));
		return "/WEB-INF/jsp/login/view-user.jsp";
	}

	private byte[] getQRCode(User dbUser) {
		List<UserRole> userRoles= userRoleService.getUserRolesByUserId(getServiceContext(), dbUser.getId());
		String roleInfo="";
		if(userRoles!=null&&!userRoles.isEmpty()){
			for(int i=0;i<userRoles.size();i++){
				UserRole userRole=userRoles.get(i);
				roleInfo=roleInfo+";"+userRole.getRole().getRoleName();
			}
		}
		
		if(JStringUtils.isNotNullOrEmpty(roleInfo)){
			roleInfo=roleInfo.replaceFirst(";", "");
		}
		
		String userInfo=JHttpUtils.getAppUrlPath(getHttpContext().getRequest());
		byte[] bytes=JQRCode.createQRCode(userInfo);
		return bytes;
	}
	
	public String toCreateUser(){
		return "/WEB-INF/jsp/login/create-user.jsp";
	}
	
	public String createUser() throws Exception {
		String passwrod=user.getPassword();
		String encriptPassword= JRSSecurityHelper.encryptOnDESede(passwrod);
		user.setPassword(encriptPassword);
		ServiceContext context=new ServiceContext();
		context.setUser((User) getSessionUser());
		userService.saveUser(context, user);
		setAttribute("user", user);
		return "/WEB-INF/jsp/login/view-user.jsp";
	}
	
	public String toRegister(){
		return "/WEB-INF/jsp/login/register.jsp";
	}
	
	public String register() throws Exception {
		
		ServiceContext context=new ServiceContext();
		context.setUser((User) getSessionUser());
		userService.register(context, user);
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
			byte[] bytes = getQRCode(user);
			setAttribute("qrcode", JStringUtils.bytestoBASE64String(bytes));
		}
		return "/WEB-INF/jsp/login/view-user.jsp"; 
	}
	
	
	public String toUploadImage(){
		SessionUser sessionUser=getSessionUser();
		User dbUser=userService.getUserByName(null, sessionUser.getUserName());
		setAttribute("user", dbUser);
		return "/WEB-INF/jsp/login/image-user.jsp";
	}
	
	public String uploadImage(){
		SessionUser sessionUser=getSessionUser();
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
		
		JServiceHubDelegate.get().addDelayEvent(new ResourceCachedRefreshEvent(this, JAPPEvent.HIGEST));

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
	
	
	public String toRoleAllocationOnGroup(){
		List<Role> roles =roleService.getAllRoles(getServiceContext());
		setAttribute("roles", roles);
		
		List<Group> groups =groupService.getAllGroups(getServiceContext());
		setAttribute("groups", groups);
//		return navigate("/login.loginaction/testtoUserAuthorized");
//		return getTestPage("/WEB-INF/jsp/login/user-authorized.jsp");
		return "/WEB-INF/jsp/login/role-allocation-on-group.jsp";
	}
	
	
	public String getRoleGroupByRole(){
		String roleId=getParameter("roleId").trim();
		List<RoleGroup> roleGroups= roleGroupService.getRoleGroupsByRoleId(getServiceContext(), roleId);
		return JJSON.get().format(roleGroups); 
	}
	
	
	public String bingRoleOnGroup() throws Exception{
		String groupId=getParameter("groupId").trim();
		String roleId=getParameter("roleId").trim();
		roleGroupService.bingRoleGroup(getServiceContext(), roleId, groupId);
		List<RoleGroup> roleGroups= roleGroupService.getRoleGroupsByRoleId(getServiceContext(), roleId);
		return JJSON.get().format(roleGroups); 
	}
	
	public String unbingRoleOnGroup() throws Exception{
		String groupId=getParameter("groupId").trim();
		String roleId=getParameter("roleId").trim();
		roleGroupService.unbingRoleGroup(getServiceContext(), roleId, groupId);
		List<RoleGroup> roleGroups= roleGroupService.getRoleGroupsByRoleId(getServiceContext(), roleId);
		return JJSON.get().format(roleGroups); 
	}
	
	
	public String toGroupAllocationOnRole(){
		List<Role> roles =roleService.getAllRoles(getServiceContext());
		setAttribute("roles", roles);
		
		List<Group> groups =groupService.getAllGroups(getServiceContext());
		setAttribute("groups", groups);
//		return navigate("/login.loginaction/testtoUserAuthorized");
//		return getTestPage("/WEB-INF/jsp/login/user-authorized.jsp");
		return "/WEB-INF/jsp/login/group-allocation-on-role.jsp";
	}
	
	public String getGroupRoleByGroup(){
		String groupId=getParameter("groupId").trim();
		List<RoleGroup> roleGroups= roleGroupService.getRoleGroupsByGroupId(getServiceContext(), groupId);
		return JJSON.get().format(roleGroups); 
	}
	
	
	public String bingGroupOnRole() throws Exception{
		String groupId=getParameter("groupId").trim();
		String roleId=getParameter("roleId").trim();
		roleGroupService.bingRoleGroup(getServiceContext(), roleId, groupId);
		List<RoleGroup> roleGroups= roleGroupService.getRoleGroupsByGroupId(getServiceContext(), groupId);
		return JJSON.get().format(roleGroups); 
	}
	
	public String unbingGroupOnRole() throws Exception{
		String groupId=getParameter("groupId").trim();
		String roleId=getParameter("roleId").trim();
		roleGroupService.unbingRoleGroup(getServiceContext(), roleId, groupId);
		List<RoleGroup> roleGroups= roleGroupService.getRoleGroupsByGroupId(getServiceContext(), groupId);
		return JJSON.get().format(roleGroups); 
	}
	
	
	
	public String toAddNewRole(){
		return "/WEB-INF/jsp/login/role-new-add.jsp";
	}

	public String addNewRole() throws Exception{
		roleService.saveRole(getServiceContext(), role);
		setSuccessMessage(CREATE_SUCCESS);
		return "/WEB-INF/jsp/login/role-new-add.jsp";
	}
	
	
	public String toEditRole(){
		String roleId=getParameter("roleId").trim();
		
		Role role=roleService.getById(getServiceContext(), roleId);
		setAttribute("role", role);
		return "/WEB-INF/jsp/login/role-edit.jsp";
	}
	
	public String editRole() throws Exception{
		roleService.updateRole(getServiceContext(), role);
		Role dbRole=roleService.getById(getServiceContext(), role.getId());
		setAttribute("role", dbRole);
		setSuccessMessage(EDIT_SUCCESS);
		return "/WEB-INF/jsp/login/role-edit.jsp"; 
	}
	
	public String deleteRole() throws Exception{
		String roleId=getParameter("roleId").trim();
		Role role=new Role();
		role.setId(roleId); 
		roleService.deleteRole(getServiceContext(), role);
		List<Role> roles =roleService.getAllRoles(getServiceContext());
		return JJSON.get().format(roles); 
	}
	
	
	
	public String toAddNewGroup(){
		return "/WEB-INF/jsp/login/group-new-add.jsp";
	}

	public String addNewGroup() throws Exception{
		groupService.saveGroup(getServiceContext(), group);
		setSuccessMessage(CREATE_SUCCESS);
		return "/WEB-INF/jsp/login/group-new-add.jsp";
	}
	
	
	public String toEditGroup(){
		String groupId=getParameter("groupId").trim();
		
		Group group=groupService.getById(getServiceContext(), groupId);
		setAttribute("group", group);
		return "/WEB-INF/jsp/login/group-edit.jsp";
	}
	
	public String editGroup() throws Exception{
		groupService.updateGroup(getServiceContext(), group);
		Group dbGroup=groupService.getById(getServiceContext(), group.getId());
		setAttribute("group", dbGroup);
		setSuccessMessage(EDIT_SUCCESS);
		return "/WEB-INF/jsp/login/group-edit.jsp"; 
	}
	
	public String deleteGroup() throws Exception{
		String groupId=getParameter("groupId").trim();
		Group group=new Group();
		group.setId(groupId);
		groupService.deleteGroup(getServiceContext(), group);
		List<Group> groups =groupService.getAllGroups(getServiceContext());
		return JJSON.get().format(groups); 
	}
	
	
}
