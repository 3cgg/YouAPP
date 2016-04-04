package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;

public interface AuthenticationHookService extends JService{

	public void doAfterLogin(HttpContext httpContext);
	
	public void doAfterLoginout(HttpContext httpContext);
	
}
