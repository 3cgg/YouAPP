/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.components.authorize.resource.model.ResourceAuthorized;
import j.jave.framework.components.authorize.resource.service.ResourceAuthorizedService;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.servicehub.ServiceHubDelegate;
import j.jave.framework.components.login.ehcache.ResourceIO;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.service.UserGroupService;
import j.jave.framework.components.login.service.UserRoleService;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.resource.service.ResourceGroupService;
import j.jave.framework.components.resource.service.ResourceRoleService;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;
import j.jave.framework.components.support.ehcache.subhub.EhcacheServiceSupport;
import j.jave.framework.security.JAPPCipher;
import j.jave.framework.utils.JUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="loginAccessService")
public class LoginAccessServiceImpl implements LoginAccessService ,ResourceIO ,EhcacheServiceSupport{

	private static final String RESOURCE_AUTHORIZED_KEY="j.jave.framework.components.login.ehcache.user.resource.authorized";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private ResourceGroupService resourceGroupService;
	
	@Autowired
	private ResourceRoleService resourceRoleService;
	
	@Autowired
	private ResourceAuthorizedService resourceAuthorizedService;
	
	/**
	 * cache service . 
	 */
	private EhcacheService ehcacheService=null;;
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.UserAccessService#validate(java.lang.String, java.lang.String)
	 */
	@Override
	public String validate(String name, String password)
			throws ServiceException {
		if(JUtils.isNullOrEmpty(name)){
			throw new ServiceException("用户名不能为空");
		}
		
		if(JUtils.isNullOrEmpty(password)){
			throw new ServiceException("密码不能为空");
		}
		String encryptPassword=JAPPCipher.get().encrypt(password.trim());
		User user= userService.getUserByNameAndPassword(name.trim(), encryptPassword);
		if(user!=null) return  JUtils.unique()+"-"+name; 
		return "";
	}
	
	
	@Override
	public boolean isNeedLoginRole(String url) throws ServiceException {
		if("/login.loginaction/toRegister".equals(url)){
			return false;
		}
		else if("/login.loginaction/register".equals(url)){
			return false;
		}
		else if("/login.loginaction/login".equals(url)){
			return false;
		}
		else if("/login.loginaction/toLogin".equals(url)){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.LoginAccessService#register(j.jave.framework.components.login.model.User)
	 */
	@Override
	public void register(ServiceContext context,User user) throws ServiceException {
		
		if(JUtils.isNullOrEmpty(user.getUserName())){
			throw new ServiceException("用户名不能为空");
		}
		
		if(JUtils.isNullOrEmpty(user.getPassword())){
			throw new ServiceException("密码不能为空");
		}		
		
		if(!user.getPassword().equals(user.getRetypePassword())){
			throw new ServiceException("两次输入的密码不一样");
		}
		
		User dbUser=userService.getUserByName(context, user.getUserName().trim());
		if(dbUser!=null){
			throw new ServiceException("用户已经存在");
		}
		
		String passwrod=user.getPassword().trim();
		String encriptPassword=JAPPCipher.get().encrypt(passwrod);
		user.setPassword(encriptPassword);
		user.setUserName(user.getUserName().trim());
		userService.saveUser(context, user);  // with encrypted password 
	}
	
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.service.LoginAccessService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public String login(String name, String password) throws ServiceException {
		String ticket=validate(name, password);
		if(JUtils.isNullOrEmpty(ticket)){
			throw new ServiceException("用户名或者密码不正确");
		}
		return ticket;
	}
	
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.login.service.LoginAccessService#authorize(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authorizeOnUserName(String resource, String name) {
		
		User user=userService.getUserByName(null, name);
		return authorizeOnUserId(resource, user.getId());
		
	}
	
	
	@Override
	public boolean authorizeOnUserId(String resource, String userId) {
		Map<String, List<String>> resources=(Map<String, List<String>>) getEhcacheService().get(RESOURCE_AUTHORIZED_KEY);
		if(resources==null){
			resources=(Map<String, List<String>>) set();
		}
		List<String> urls= resources.get(userId);
		if(urls==null||urls.isEmpty()||!urls.contains(resource)){
			return false;
		}
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object set() {
		
		Map<String, List<String>> resources=new HashMap<String, List<String>>();
		List<User> users=userService.getUsers();
		if(users!=null){
			for(int i=0;i<users.size();i++){
				User user=users.get(i);
				List<String> resourceUrls=new ArrayList<String>();
				List<ResourceAuthorized> resourceAuthorizeds= resourceAuthorizedService.getResourceAuthorizedByUserId(user.getId());
				if(resourceAuthorizeds!=null){
					for(int j=0;j<resourceAuthorizeds.size();j++){
						ResourceAuthorized resourceAuthorized=resourceAuthorizeds.get(j);
						resourceUrls.add(resourceAuthorized.getUrl());
					}
				}
				resources.put(user.getId(), resourceUrls);
			}
		}
		getEhcacheService().put(RESOURCE_AUTHORIZED_KEY, resources);
		
		return resources;
	}
	
	@Override
	public EhcacheService getEhcacheService() {
		if(ehcacheService==null){
			ehcacheService=new ServiceHubDelegate().getService(this, EhcacheService.class);
		}
		return ehcacheService;
	}
}
