package j.jave.platform.basicwebcomp.web.youappmvc.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.service.JService;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;

public interface PageableService extends JService{

	public JPage parse(HttpContext httpContext);
	
}
