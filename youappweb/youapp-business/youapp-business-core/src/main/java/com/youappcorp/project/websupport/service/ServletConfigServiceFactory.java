package com.youappcorp.project.websupport.service;

import j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigService;

import org.springframework.stereotype.Service;

@Service(value="com.youappcorp.project.core.service.ServletConfigServiceFactory")
public class ServletConfigServiceFactory extends j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigServiceFactory {
	
	public ServletConfigServiceFactory() {
		super(ServletConfigService.class);
	}
	
}
