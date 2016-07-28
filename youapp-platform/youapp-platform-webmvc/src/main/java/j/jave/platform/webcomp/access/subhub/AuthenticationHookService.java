package j.jave.platform.webcomp.access.subhub;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.webcomp.web.youappmvc.ServletHttpContext;

public interface AuthenticationHookService extends JService{

	public void doAfterLogin(ServletHttpContext httpContext);
	
	public void doAfterLoginout(ServletHttpContext httpContext);
	
}
