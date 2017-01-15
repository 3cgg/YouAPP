package me.bunny.modular._p.streaming.netty.examples;

import me.bunny.modular._p.streaming.netty.controller.ControllerService;
import me.bunny.modular._p.streaming.netty.controller.JRequestMapping;

@JRequestMapping(path="/superunit")
public interface IUnitSuperController extends ControllerService{

	@JRequestMapping(path="/superName")
	String superName(String name);
}
