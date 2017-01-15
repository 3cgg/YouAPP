package com.youappcorp.project.websupport.service;

import org.springframework.stereotype.Service;

import me.bunny.app._c._web.web.youappmvc.subhub.servletconfig.ServletConfigService;

@Service(value="ServletConfigServiceFactory")
public class ServletConfigServiceFactory extends me.bunny.app._c._web.web.youappmvc.subhub.servletconfig.ServletConfigServiceFactory {
	
	public ServletConfigServiceFactory() {
		super(ServletConfigService.class);
	}
	
}
