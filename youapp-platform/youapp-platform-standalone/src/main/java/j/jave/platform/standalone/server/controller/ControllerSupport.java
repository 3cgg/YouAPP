package j.jave.platform.standalone.server.controller;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedListener;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.List;

public abstract class ControllerSupport<T extends JService> 
extends JServiceFactorySupport<T>
implements JControllerService , JControllerServiceFactory, JServiceHubInitializedListener {
	
	@Override
	public JControllerService getControllerService() {
		return this;
	}
	
	@Override
	public Object trigger(JServiceHubInitializedEvent event) {
    	
    	ClassProvidedMappingDetector mappingDetector=new ClassProvidedMappingDetector(getClass());
		mappingDetector.detect();
		List<MappingMeta> mappingMetas= mappingDetector.getMappingMetas();
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
	
}
