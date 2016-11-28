package j.jave.kernal.streaming.netty.examples;

import j.jave.kernal.streaming.netty.controller.IControllerImplementer;
import j.jave.kernal.streaming.netty.controller.JRequestMapping;


@JRequestMapping(path="/unit")
public interface IUnitController extends IControllerImplementer<UnitController>
,IUnitSuperController,IJVMController{

	@JRequestMapping(path="/name")
	Object name(String unique);

}