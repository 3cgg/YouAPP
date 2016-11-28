package j.jave.kernal.streaming.netty.examples;

import j.jave.kernal.streaming.netty.controller.ControllerService;

public interface IJVMController extends ControllerService{

	public Object jvmVersion();
	
	public Object jvm();
	
}
