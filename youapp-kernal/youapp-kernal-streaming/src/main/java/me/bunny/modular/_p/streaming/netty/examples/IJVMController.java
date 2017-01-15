package me.bunny.modular._p.streaming.netty.examples;

import me.bunny.modular._p.streaming.netty.controller.ControllerService;

public interface IJVMController extends ControllerService{

	public Object jvmVersion();
	
	public Object jvm();
	
}
