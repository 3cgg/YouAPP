package me.bunny.app._c._web.web.youappmvc.service;

import me.bunny.app._c._web.WebCompProperties;
import me.bunny.app._c._web.web.youappmvc.plugins.pageable.JQueryDataTablePageService;
import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.service.JService;

public class PageableServiceFactory<T extends JService> extends SpringServiceFactorySupport<T> {
	
	@Override
	protected boolean isCanRegister() {
		String pagebaleString=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_PAGEABLE_SERVICE_FACTORY, 
				JQueryDataTablePageService.class.getName());
		return pagebaleString.equals(this.getClass().getName());
	}
}
