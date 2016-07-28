package j.jave.platform.webcomp.access.subhub;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.webcomp.web.youappmvc.ServletHttpContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="AuthenticationHookDelegateService")
public class AuthenticationHookDelegateService 
extends SpringServiceFactorySupport<AuthenticationHookDelegateService>
implements JService,AuthenticationHookService
{
	@Autowired(required=false)
	private AuthenticationHookService authenticationHookService;
	

	@Override
	public void doAfterLogin(ServletHttpContext httpContext) {
		if(authenticationHookService!=null){
			authenticationHookService.doAfterLogin(httpContext);
		}
	}
	
	@Override
	public void doAfterLoginout(ServletHttpContext httpContext) {
		if(authenticationHookService!=null){
			authenticationHookService.doAfterLoginout(httpContext);
		}
	}
	
	
	
	
}
