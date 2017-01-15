package j.jave.platform.standalone.server.controller;

import java.util.List;

import me.bunny.kernel._c.exception.JInitializationException;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public abstract class ControllerSupport<T extends JService> 
extends JServiceFactorySupport<T>
implements ControllerService , ControllerServiceFactory, ControllerServiceFindingListener {
	
	@Override
	public ControllerService getControllerService() {
		return this;
	}
	
	@Override
	public Object trigger(ControllerServiceFindingEvent event) {
    	ClassProvidedMappingFinder mappingFinder=new ClassProvidedMappingFinder(getClass());
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
		return "the controller ["+this.getClass().getName()+"] is internally registered  OK";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T doGetService() {
		return (T) this;
	}
}
