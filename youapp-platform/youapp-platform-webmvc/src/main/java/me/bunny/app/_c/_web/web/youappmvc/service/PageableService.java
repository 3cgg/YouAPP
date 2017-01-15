package me.bunny.app._c._web.web.youappmvc.service;

import me.bunny.app._c._web.web.youappmvc.HttpContext;
import me.bunny.kernel._c.model.JPageable;
import me.bunny.kernel._c.service.JService;

public interface PageableService extends JService{

	public JPageable parse(HttpContext httpContext);
	
}
