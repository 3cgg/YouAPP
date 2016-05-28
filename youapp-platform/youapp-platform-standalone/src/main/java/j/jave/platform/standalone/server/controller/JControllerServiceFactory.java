package j.jave.platform.standalone.server.controller;

import j.jave.kernal.jave.service.JService;

public interface JControllerServiceFactory extends JService{

	/**
	 * it's recommended the object should be single.
	 * @return
	 */
	JControllerService getControllerService();
	
	String getControllerServiceName();
	
}
