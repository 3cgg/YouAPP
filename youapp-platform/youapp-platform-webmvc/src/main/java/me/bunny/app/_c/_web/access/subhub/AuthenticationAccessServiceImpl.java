/**
 * 
 */
package me.bunny.app._c._web.access.subhub;

import me.bunny.app._c._web.WebCompProperties;
import me.bunny.app._c._web.core.service.SessionUserImpl;
import me.bunny.app._c._web.web.cache.resource.weburl.WebRequestURLCacheModel;
import me.bunny.app._c._web.web.cache.resource.weburl.WebRequestURLCacheService;
import me.bunny.app._c._web.web.youappmvc.container.HttpInvokeContainerDelegateService;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.utils.JUniqueUtils;
import me.bunny.kernel.eventdriven.exception.JServiceException;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/**
 * @author J
 *
 */
@Service(value=AuthenticationAccessServiceImpl.BEAN_NAME)
public class AuthenticationAccessServiceImpl implements AuthenticationAccessService{
	
	public static final String BEAN_NAME="default-authenticationAccessServiceImpl";
	
	private AuthenticationManagerService authenticationManagerService=
			JServiceHubDelegate.get().getService(this, AuthenticationManagerService.class);;
	
	private WebRequestURLCacheService webRequestURLCacheService=
			JServiceHubDelegate.get().getService(this, WebRequestURLCacheService.class);
	
	private HttpInvokeContainerDelegateService httpInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	private boolean authorizeOpen;
	
	private List<Pattern> excludes=new ArrayList<Pattern>();
	{
		authorizeOpen=JConfiguration.get().getBoolean(WebCompProperties.YOUAPPMVC_RESOURCE_AUTHORIZATION_ONOFF, true);
		String patterns=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_RESOURCE_AUTHORIZATION_EXCLUDE_PATTERN, "");
		for(String string:patterns.split(",")){
			if(JStringUtils.isNotNullOrEmpty(string)){
				excludes.add(Pattern.compile(string));
			}
		}
		if(excludes.isEmpty()){
			excludes.add(Pattern.compile(".+"));
		}
	}
	
	@Override
	public SessionUserImpl validate(String name, String password)
			throws JServiceException {
		if(JStringUtils.isNullOrEmpty(name)){
			throw new JServiceException("用户名不能为空");
		}
		
		if(JStringUtils.isNullOrEmpty(password)){
			throw new JServiceException("密码不能为空");
		}
		SessionUserImpl user= authenticationManagerService.getUserByNameAndPassword(name.trim(), password);
		if(user!=null){
			user.setTicket(JUniqueUtils.unique());
			return user;
		} 
		return null;
	}
	
	
	@Override
	public boolean isNeedAuthorize(String url) throws JServiceException {
		if(authorizeOpen){
			if(JStringUtils.isNullOrEmpty(url)){
				return false;
			}
			boolean need=true;
			for(Pattern pattern:excludes){
				if(pattern.matcher(url).matches()){
					need=false;
					break;
				}
			}
			return need;
		}
		return false;
	}

	@Override
	public SessionUserImpl login(String name, String password) throws JServiceException {
		return validate(name, password);
	}
	
	
	@Override
	public boolean authorizeOnUserName(String resource, String name) {
		WebRequestURLCacheModel webRequestURLCacheModel=   webRequestURLCacheService.get(resource);
		if(webRequestURLCacheModel==null)
			return true;
		List<String> accessUserNames=webRequestURLCacheModel.accessUserNames();
		return accessUserNames.contains(name);
	}
	
	
	@Override
	public boolean authorizeOnUserId(String resource, String userId) {
		WebRequestURLCacheModel webRequestURLCacheModel=   webRequestURLCacheService.get(resource);
		if(webRequestURLCacheModel==null)
			return true;
		List<String> accessUserIds=webRequestURLCacheModel.accessUserIds();
		return accessUserIds.contains(userId);
	}
	
	@Override
	public boolean isValidResource(String resource) {
		WebRequestURLCacheModel webRequestURLCacheModel=   webRequestURLCacheService.get(resource);
		boolean valid=webRequestURLCacheModel!=null;
		if(!valid){
			valid=valid||httpInvokeContainerDelegateService.exist(resource);
		}
		return valid;
	}

}
