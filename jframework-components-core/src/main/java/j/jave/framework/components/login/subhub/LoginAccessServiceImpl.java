/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.components.authorize.resource.model.ResourceAuthorized;
import j.jave.framework.components.authorize.resource.service.ResourceAuthorizedService;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.servicehub.ServiceHubDelegate;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.service.UserGroupService;
import j.jave.framework.components.login.service.UserRoleService;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.resource.service.ResourceGroupService;
import j.jave.framework.components.resource.service.ResourceRoleService;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;
import j.jave.framework.components.support.ehcache.subhub.EhcacheServiceSupport;
import j.jave.framework.io.memory.JStaticMemoryCacheIO;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.support.security.JAPPCipher;
import j.jave.framework.utils.JStringUtils;
import j.jave.framework.utils.JUniqueUtils;

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
public class LoginAccessServiceImpl implements LoginAccessService ,JStaticMemoryCacheIO ,EhcacheServiceSupport{

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
			throws JServiceException {
		if(JStringUtils.isNullOrEmpty(name)){
			throw new JServiceException("用户名不能为空");
		}
		
		if(JStringUtils.isNullOrEmpty(password)){
			throw new JServiceException("密码不能为空");
		}
		String encryptPassword=JAPPCipher.get().encrypt(password.trim());
		User user= userService.getUserByNameAndPassword(name.trim(), encryptPassword);
		if(user!=null) return  JUniqueUtils.unique()+"-"+name; 
		return "";
	}
	
	
	@Override
	public boolean isNeedLoginRole(String url) throws JServiceException {
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
	public void register(ServiceContext context,User user) throws JServiceException {
		
		if(JStringUtils.isNullOrEmpty(user.getUserName())){
			throw new JServiceException("用户名不能为空");
		}
		
		if(JStringUtils.isNullOrEmpty(user.getPassword())){
			throw new JServiceException("密码不能为空");
		}		
		
		if(!user.getPassword().equals(user.getRetypePassword())){
			throw new JServiceException("两次输入的密码不一样");
		}
		
		User dbUser=userService.getUserByName(context, user.getUserName().trim());
		if(dbUser!=null){
			throw new JServiceException("用户已经存在");
		}
		
		String passwrod=user.getPassword().trim();
		String encriptPassword=JAPPCipher.get().encrypt(passwrod);
		user.setPassword(encriptPassword);
		user.setUserName(user.getUserName().trim());
		userService.saveUser(context, user);  // with encrypted password 
	}
	
	
	@Override
	public String login(String name, String password) throws JServiceException {
		String ticket=validate(name, password);
		if(JStringUtils.isNullOrEmpty(ticket)){
			throw new JServiceException("用户名或者密码不正确");
		}
		return ticket;
	}
	
	
	@Override
	public boolean authorizeOnUserName(String resource, String name) {
		
		User user=userService.getUserByName(null, name);
		return authorizeOnUserId(resource, user.getId());
		
	}
	
	
	@Override
	public boolean authorizeOnUserId(String resource, String userId) {
		Map<String, List<String>> resources=get();
		if(resources==null){
			resources= set();
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
	public Map<String, List<String>> set() {
		
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<String>> get() {
		return (Map<String, List<String>>) getEhcacheService().get(RESOURCE_AUTHORIZED_KEY);
	}
	
	@Override
	public EhcacheService getEhcacheService() {
		if(ehcacheService==null){
			ehcacheService=ServiceHubDelegate.get().getService(this, EhcacheService.class);
		}
		return ehcacheService;
	}
}
