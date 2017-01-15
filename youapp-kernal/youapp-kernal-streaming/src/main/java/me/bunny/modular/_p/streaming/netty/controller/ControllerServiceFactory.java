package me.bunny.modular._p.streaming.netty.controller;

import me.bunny.kernel._c.service.JService;

public interface ControllerServiceFactory extends JService{

	/**
	 * it's recommended the object should be single.
	 * @return
	 */
	ControllerService getControllerService();
	
	String getControllerServiceName();
	
}
