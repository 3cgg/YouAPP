package j.jave.platform.webcomp.web.youappmvc.service;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.webcomp.WebCompProperties;
import j.jave.platform.webcomp.web.youappmvc.plugins.pageable.JQueryDataTablePageService;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.service.JService;

public class PageableServiceFactory<T extends JService> extends SpringServiceFactorySupport<T> {
	
	@Override
	protected boolean isCanRegister() {
		String pagebaleString=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_PAGEABLE_SERVICE_FACTORY, 
				JQueryDataTablePageService.class.getName());
		return pagebaleString.equals(this.getClass().getName());
	}
}
