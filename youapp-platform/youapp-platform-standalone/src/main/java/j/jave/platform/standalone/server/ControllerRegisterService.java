package j.jave.platform.standalone.server;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.service.JService;

public class ControllerRegisterService extends JServiceFactorySupport<ControllerRegisterService>
implements JService{
	
	
	
	@Override
	protected ControllerRegisterService doGetService() {
		return this;
	}
	
	
	

}
