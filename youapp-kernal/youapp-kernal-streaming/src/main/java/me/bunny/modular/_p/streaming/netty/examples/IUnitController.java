package me.bunny.modular._p.streaming.netty.examples;

import me.bunny.modular._p.streaming.netty.controller.IControllerImplementer;
import me.bunny.modular._p.streaming.netty.controller.JRequestMapping;


@JRequestMapping(path="/unit")
public interface IUnitController extends IControllerImplementer<UnitController>
,IUnitSuperController,IJVMController{

	@JRequestMapping(path="/name")
	Object name(String unique);

}