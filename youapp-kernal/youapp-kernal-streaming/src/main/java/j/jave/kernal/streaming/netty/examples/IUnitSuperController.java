package j.jave.kernal.streaming.netty.examples;

import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.controller.JRequestMapping;

@JRequestMapping(path="/superunit")
public interface IUnitSuperController extends ControllerService{

	@JRequestMapping(path="/superName")
	String superName(String name);
}
