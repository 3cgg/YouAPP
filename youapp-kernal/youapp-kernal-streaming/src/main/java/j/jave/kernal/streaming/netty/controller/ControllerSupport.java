package j.jave.kernal.streaming.netty.controller;

import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.utils.JStringUtils;

public abstract class ControllerSupport<T extends ControllerSupport<T>> 
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
			findOnAnnotation(intarface);
			findOnInterface(intarface);
		}
		
		findOnClass(this.getClass());
    	
		return "the controller ["+this.getClass().getName()+"] is internally registered  OK";
	}
	
	private void findOnClass(Class<?> clazz) {
		ClassProvidedMappingFinder mappingFinder=new ClassProvidedMappingFinder(clazz,ClassProvidedMappingFinder.CLASS);
		List<MappingMeta> mappingMetas= mappingFinder.find().getMappingMetas();
		_addMappingMeta0(mappingMetas);
	}
	
	private void findOnInterface(Class<?> intarface) {
		ClassProvidedMappingFinder mappingFinder=new ClassProvidedMappingFinder(intarface,ClassProvidedMappingFinder.INTERFACE);
		List<MappingMeta> mappingMetas= mappingFinder.find().getMappingMetas();
		_addMappingMeta0(mappingMetas);
	}

	private void _addMappingMeta0(List<MappingMeta> mappingMetas) {
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

	private void findOnAnnotation(Class<?> intarface) {
		ClassProvidedMappingFinder mappingFinder=new ClassProvidedMappingFinder(intarface);
		List<MappingMeta> mappingMetas= mappingFinder.find().getMappingMetas();
		_addMappingMeta0(mappingMetas);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T doGetService() {
		return (T) this;
	}
}
