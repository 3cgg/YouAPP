package j.jave.platform.webcomp.access.subhub;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel._c.service.JService;

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
