package j.jave.platform.standalone.server.controller;

import j.jave.kernal.jave.service.JService;

public interface ControllerServiceFactory extends JService{

	/**
	 * it's recommended the object should be single.
	 * @return
	 */
	ControllerService getControllerService();
	
	String getControllerServiceName();
	
}
