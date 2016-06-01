package j.jave.platform.standalone.server;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

public class ControllerRegisterService extends JServiceFactorySupport<ControllerRegisterService>
implements JService{
	
	
	
	@Override
	public ControllerRegisterService getService() {
		return this;
	}
	
	
	

}
