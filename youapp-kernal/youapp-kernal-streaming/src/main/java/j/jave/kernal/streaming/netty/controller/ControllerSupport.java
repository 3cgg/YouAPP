package j.jave.kernal.streaming.netty.controller;

import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JStringUtils;

public abstract class ControllerSupport<T extends JService> 
extends JServiceFactorySupport<T>
implements ControllerService , ControllerServiceFactory, ControllerServiceFindingListener {
	
	public ControllerSupport() {
		super();
	}
	
	@Override
	public ControllerService getControllerService() {
		return this;
	}
	
	private void allInterfaces(Class<?> clazz,List<Class<?>> interfaces){
		_Util.allInterfaces(clazz, interfaces);
	}
	
	@Override
	public Object trigger(ControllerServiceFindingEvent event) {
		List<Class<?>> interfaces=new ArrayList<>();
		allInterfaces(getClass(), interfaces);
		for(Class<?> intarface:interfaces){
			ClassProvidedMappingFinder mappingFinder=new ClassProvidedMappingFinder(intarface);
			List<MappingMeta> mappingMetas= mappingFinder.find().getMappingMetas();
			for(MappingMeta meta:mappingMetas){
				String controllerServiceName=getControllerServiceName();
				if(JStringUtils.isNullOrEmpty(controllerServiceName)){
					throw new JInitializationException("the controller name ["+this.getClass().getName()
							+"] must be provided.");
				}
				meta.setControllerName(getControllerServiceName());
				meta.setControllerServiceFactory(this);
				MappingControllerManager.get().putMappingMeta(meta.getPath(), meta);
			}
		}
    	
		return "the controller ["+this.getClass().getName()+"] is internally registered  OK";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T doGetService() {
		return (T) this;
	}
}
