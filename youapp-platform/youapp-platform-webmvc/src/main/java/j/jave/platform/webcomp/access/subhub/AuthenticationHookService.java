package j.jave.platform.webcomp.access.subhub;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel._c.service.JService;

public interface AuthenticationHookService extends JService{

	public void doAfterLogin(HttpContext httpContext);
	
	public void doAfterLoginout(HttpContext httpContext);
	
}
