package com.youappcorp.project.core.service;

import org.springframework.stereotype.Service;

import j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigService;

@Service(value="com.youappcorp.project.core.service.ServletConfigServiceFactory")
public class ServletConfigServiceFactory extends j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigServiceFactory {
	
	public ServletConfigServiceFactory() {
		super(ServletConfigService.class);
	}
	
}
