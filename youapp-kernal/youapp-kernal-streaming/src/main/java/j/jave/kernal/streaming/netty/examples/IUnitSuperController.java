package j.jave.kernal.streaming.netty.examples;

import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.controller.JRequestMapping;

@JRequestMapping(path="/unit")
public interface IUnitSuperController extends ControllerService{

	@JRequestMapping(path="/sup")
	String sup(String name);
}
