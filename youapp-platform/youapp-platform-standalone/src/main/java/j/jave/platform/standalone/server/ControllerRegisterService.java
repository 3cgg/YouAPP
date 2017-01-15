package j.jave.platform.standalone.server;

import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class ControllerRegisterService extends JServiceFactorySupport<ControllerRegisterService>
implements JService{
	
	
	
	@Override
	protected ControllerRegisterService doGetService() {
		return this;
	}
	
	
	

}
