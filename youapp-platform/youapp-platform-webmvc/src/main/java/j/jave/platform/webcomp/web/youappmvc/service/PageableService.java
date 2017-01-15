package j.jave.platform.webcomp.web.youappmvc.service;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel._c.model.JPageable;
import me.bunny.kernel._c.service.JService;

public interface PageableService extends JService{

	public JPageable parse(HttpContext httpContext);
	
}
