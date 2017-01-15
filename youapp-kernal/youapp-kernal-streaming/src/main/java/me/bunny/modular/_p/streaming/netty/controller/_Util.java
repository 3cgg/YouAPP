package me.bunny.modular._p.streaming.netty.controller;

import java.util.List;

public abstract class _Util {

	public static void allInterfaces(Class<?> clazz,List<Class<?>> interfaces){
		if(ControllerService.class.isAssignableFrom(clazz)
				&&IControllerImplementer.class!=clazz
				&&ControllerService.class!=clazz
				&&clazz.isInterface()){
			interfaces.add(clazz);
		}
		Class<?>[] directInterfaces=clazz.getInterfaces();
		for(Class<?> intarface:directInterfaces){
			allInterfaces(intarface, interfaces);
		}
	}
	
}
