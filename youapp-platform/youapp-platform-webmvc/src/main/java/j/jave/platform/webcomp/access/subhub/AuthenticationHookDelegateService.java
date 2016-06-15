package j.jave.platform.webcomp.access.subhub;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="j.jave.platform.basicwebcomp.access.subhub.AuthenticationHookDelegateService")
public class AuthenticationHookDelegateService 
extends SpringServiceFactorySupport<AuthenticationHookDelegateService>
implements JService,AuthenticationHookService
{
	@Autowired(required=false)
	private AuthenticationHookService authenticationHookService;
	

	@Override
	public void doAfterLogin(HttpContext httpContext) {
		if(authenticationHookService!=null){
			authenticationHookService.doAfterLogin(httpContext);
		}
	}
	
	@Override
	public void doAfterLoginout(HttpContext httpContext) {
		if(authenticationHookService!=null){
			authenticationHookService.doAfterLoginout(httpContext);
		}
	}
	
	
	
	
}
