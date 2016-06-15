package j.jave.platform.webcomp.web.youappmvc.service;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.service.JService;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

public interface PageableService extends JService{

	public JPageable parse(HttpContext httpContext);
	
}
