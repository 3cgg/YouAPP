package me.bunny.app._c._web.access.subhub;

import me.bunny.app._c._web.web.youappmvc.HttpContext;
import me.bunny.kernel._c.service.JService;

public interface AuthenticationHookService extends JService{

	public void doAfterLogin(HttpContext httpContext);
	
	public void doAfterLoginout(HttpContext httpContext);
	
}
