package j.jave.kernal.streaming.netty.client;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.service.JService;

public class ControllerCallPromiseService 
extends JServiceFactorySupport<ControllerCallPromiseService>
implements JService , ControllerCallPromiseListener{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(ControllerCallPromiseService.class);
	
	@Override
	public Object trigger(ControllerCallPromiseEvent event) {
		try{
			final ControllerCallPromise callPromise=event.getCallPromise();
			callPromise.notifyListeners();
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	@Override
	protected ControllerCallPromiseService doGetService() {
		return this;
	}
	
	
	
	

}
