package j.jave.kernal.streaming.netty.examples;

import j.jave.kernal.streaming.netty.controller.IControllerImplementer;
import j.jave.kernal.streaming.netty.controller.JRequestMapping;


@JRequestMapping(path="/unit")
public interface IUnitController extends IControllerImplementer<UnitController>
,IUnitSuperController{

	@JRequestMapping(path="/rd")
	Object rd(String unique);

	@JRequestMapping(path="/jvmversion")
	Object jvmVersion();

	@JRequestMapping(path="/jvm")
	Object jvm();
	
	

}