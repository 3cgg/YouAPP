/**
 * 
 */
package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.SessionUserImpl;
import j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheModel;
import j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheService;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.MappingController;
import j.jave.securityutil.securityclient.JRSSecurityHelper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="j.jave.platform.basicwebcomp.access.subhub.AuthenticationAccessServiceImpl")
public class AuthenticationAccessServiceImpl implements AuthenticationAccessService{
	
	@Autowired
	private AuthenticationManagerService authenticationManagerService;
	
	private WebRequestURLCacheService webRequestURLCacheService=
			JServiceHubDelegate.get().getService(this, WebRequestURLCacheService.class);
	
	@Override
	public SessionUserImpl validate(String name, String password)
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
		SessionUserImpl user= authenticationManagerService.getUserByNameAndPassword(name.trim(), encryptPassword);
		if(user!=null){
			user.setTicket(JUniqueUtils.unique());
			return user;
		} 
		return null;
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
	public SessionUserImpl login(String name, String password) throws JServiceException {
		return validate(name, password);
	}
	
	
	@Override
	public boolean authorizeOnUserName(String resource, String name) {
		
		SessionUserImpl user=authenticationManagerService.getUserByName(name);
		return authorizeOnUserId(resource, user.getUserId());
		
	}
	
	
	@Override
	public boolean authorizeOnUserId(String resource, String userId) {
		WebRequestURLCacheModel webRequestURLCacheModel=   webRequestURLCacheService.get(resource);
		List<String> accessUserIds=webRequestURLCacheModel.accessUserIds();
		return accessUserIds.contains(userId);
	}
	
	@Override
	public boolean isValidResource(String resource) {
		WebRequestURLCacheModel webRequestURLCacheModel=   webRequestURLCacheService.get(resource);
		boolean valid=webRequestURLCacheModel!=null;
		if(!valid){
			valid=valid||MappingController.urls().contains(resource);
		}
		return valid;
	}

}
