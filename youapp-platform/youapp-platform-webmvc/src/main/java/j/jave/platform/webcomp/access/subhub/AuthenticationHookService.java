package j.jave.platform.webcomp.access.subhub;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

public interface AuthenticationHookService extends JService{

	public void doAfterLogin(HttpContext httpContext);
	
	public void doAfterLoginout(HttpContext httpContext);
	
}
