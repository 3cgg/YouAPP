package j.jave.kernal.streaming.netty.controller;

import me.bunny.kernel.jave.service.JService;

public interface ControllerServiceFactory extends JService{

	/**
	 * it's recommended the object should be single.
	 * @return
	 */
	ControllerService getControllerService();
	
	String getControllerServiceName();
	
}
