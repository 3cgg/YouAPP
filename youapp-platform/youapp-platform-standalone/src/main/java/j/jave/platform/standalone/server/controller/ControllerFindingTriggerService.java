package j.jave.platform.standalone.server.controller;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedListener;
import j.jave.kernal.jave.service.JService;

public class ControllerFindingTriggerService
extends JServiceFactorySupport<ControllerFindingTriggerService>
implements JService,JServiceHubInitializedListener{

	@Override
	public Object trigger(JServiceHubInitializedEvent event) {
		return JServiceHubDelegate.get().addImmediateEvent(new ControllerServiceFindingEvent(this, JConfiguration.get()));
	}
	
	@Override
	public ControllerFindingTriggerService getService() {
		return this;
	}
	
	
}
