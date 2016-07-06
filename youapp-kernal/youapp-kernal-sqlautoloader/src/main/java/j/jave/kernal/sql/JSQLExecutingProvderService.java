package j.jave.kernal.sql;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.service.JService;

public class JSQLExecutingProvderService extends JServiceFactorySupport<JSQLExecutingProvderService>
implements JService{

	public <T extends JSQLExecutingService> T provider(Class<T> clazz ){
		return JServiceHubDelegate.get().getService(this, clazz);
	}
	
	@Override
	public JSQLExecutingProvderService getService() {
		return this;
	}
}
