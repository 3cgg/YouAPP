package j.jave.kernal.streaming.netty.test;

import j.jave.kernal.streaming.netty.controller.IControllerImplementer;

public interface IUnitController extends IControllerImplementer<UnitController> {

	Object rd(String name);

	Object jvmVersion();

	Object jvm();
	
	

}