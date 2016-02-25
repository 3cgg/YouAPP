package j.jave.platform.basicwebcomp.web.youappmvc.service;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.service.JService;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.basicwebcomp.WebCompProperties;

public class PageableServiceFactory<T extends JService> extends SpringServiceFactorySupport<T> {
	
	@Override
	protected boolean isCanRegister() {
		String pagebaleString=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_PAGEABLE_SERVICE_FACTORY, 
				"j.jave.platform.basicwebcomp.web.youappmvc.plugins.pageable.JQueryDataTablePageService");
		return pagebaleString.equals(this.getClass().getName());
	}
}
