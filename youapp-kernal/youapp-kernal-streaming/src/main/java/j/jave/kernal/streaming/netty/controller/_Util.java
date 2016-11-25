package j.jave.kernal.streaming.netty.controller;

import java.util.List;

public abstract class _Util {

	public static void allInterfaces(Class<?> clazz,List<Class<?>> interfaces){
		if(ControllerService.class.isAssignableFrom(clazz)
				&&IControllerImplementer.class!=clazz
				&&ControllerService.class!=clazz){
			interfaces.add(clazz);
		}
		Class<?>[] directInterfaces=clazz.getInterfaces();
		for(Class<?> intarface:directInterfaces){
			allInterfaces(intarface, interfaces);
		}
	}
	
}
