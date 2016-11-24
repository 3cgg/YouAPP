package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;

public class ControllerCallPromiseService 
extends JServiceFactorySupport<ControllerCallPromiseService>
implements JService , ControllerCallPromiseListener{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(ControllerCallPromiseService.class);
	
	@Override
	public Object trigger(ControllerCallPromiseEvent event) {
		try{
			final ControllerCallPromise callPromise=event.getCallPromise();
			Object object=null;
			if(callPromise.isResponsed()){
				object=callPromise.get();
			}else if((object=callPromise.cause())!=null);
			callPromise.getControllerAsyncCall().run(object);
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
