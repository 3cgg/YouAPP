/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.commons.ehcache.JEhcacheService;
import j.jave.framework.commons.ehcache.JEhcacheServiceAware;
import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.io.memory.JSingleStaticMemoryCacheIO;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.commons.utils.JUniqueUtils;
import j.jave.framework.components.authorize.resource.model.ResourceAuthorized;
import j.jave.framework.components.authorize.resource.service.ResourceAuthorizedService;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.service.UserGroupService;
import j.jave.framework.components.login.service.UserRoleService;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.components.resource.service.ResourceGroupService;
import j.jave.framework.components.resource.service.ResourceRoleService;
import j.jave.framework.components.resource.service.ResourceService;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;
import j.jave.framework.components.web.util.ControllerDetect;
import j.jave.framework.components.web.util.ControllerInfo;
import j.jave.framework.inner.support.rs.JRSSecurityHelper;

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
public class LoginAccessServiceImpl implements LoginAccessService ,JSingleStaticMemoryCacheIO<Map<String, List<String>>> ,JEhcacheServiceAware{

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
	
	@Autowired
	private ResourceService resourceService;
	
	private ControllerDetect resourceDetect=new ControllerDetect();
	
	/**
	 * cache service . 
	 */
	private EhcacheService ehcacheService=null;;
	
	@Override
	public String validate(String name, String password)
			throws JServiceException {
		if(JStringUtils.isNullOrEmpty(name)){
			throw new JServiceException("用户名不能为空");
		}
		
		if(JStringUtils.isNullOrEmpty(password)){
			throw new JServiceException("密码不能为空");
		}
		String encryptPassword=null;
		try {
			encryptPassword = JRSSecurityHelper.encryptOnDESede(password.trim());
		} catch (Exception e) {
			throw new JServiceException(e);
		}
		User user= userService.getUserByNameAndPassword(name.trim(), encryptPassword);
		if(user!=null) return  JUniqueUtils.unique(); 
		return "";
	}
	
	
	@Override
	public boolean isNeedLoginRole(String url) throws JServiceException {
		
		if(JStringUtils.isNullOrEmpty(url)){
			return false;
		}
		if("/login.loginaction/toRegister".equals(url)){
			return false;
		}
		else if("/login.loginaction/register".equals(url)){
			return false;
		}
		else if("/login.loginaction/login".equals(url)){
			return false;
		}
		else if("/mobile.login.loginaction/login".equals(url)){
			return false;
		}
//		else if("/login.loginaction/toLogin".equals(url)){
//			return false;
//		}
		return true;
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
			ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheService.class);
		}
		return ehcacheService;
	}
	
	@Override
	public void setEhcacheService(JEhcacheService ehcacheService) {
		this.ehcacheService=(EhcacheService) ehcacheService;
	}
	
	@Override
	public boolean isValidResource(String resource) {
		List<String> resources=getResources();
		if(resources!=null){
			return resources.contains(resource);
		}
		return true;
	}


	@Override
	public List<String> setResources() {
		List<Resource> resources= resourceService.getResources();
		List<String> paths=new ArrayList<String>(256);
		if(resources!=null){
			for(int i=0;i<resources.size();i++){
				Resource resource=resources.get(i);
				String path=resource.getUrl();
				if(JStringUtils.isNotNullOrEmpty(path)){
					paths.add(path);
				}
			}
		}
		
		
		List<ControllerInfo> resourceInfos= resourceDetect.detect().getMethodInfos();
		if(resourceInfos!=null){
			for(int i=0;i<resourceInfos.size();i++){
				ControllerInfo resourceInfo=resourceInfos.get(i);
				String path=resourceInfo.getPath();
				if(JStringUtils.isNotNullOrEmpty(path)&&!paths.contains(path)){
					paths.add(path);
				}
			}
		}
		getEhcacheService().put(JResourceStaticMemoryCacheIO_KEY, paths);
		return paths;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getResources() {
		Object paths= getEhcacheService().get(JResourceStaticMemoryCacheIO_KEY);
		if(paths==null){
			paths=setResources();
		}
		return (List<String>) paths;
	}
	
	
	
}
