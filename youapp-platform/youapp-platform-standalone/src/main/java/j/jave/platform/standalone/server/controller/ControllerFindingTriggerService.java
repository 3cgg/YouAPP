package j.jave.platform.standalone.server.controller;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.eventdriven.servicehub.listener.JServiceHubInitializedEvent;
import me.bunny.kernel.eventdriven.servicehub.listener.JServiceHubInitializedListener;
import me.bunny.kernel.jave.service.JService;

public class ControllerFindingTriggerService
extends JServiceFactorySupport<ControllerFindingTriggerService>
implements JService,JServiceHubInitializedListener{

	@Override
	public Object trigger(JServiceHubInitializedEvent event) {
		return JServiceHubDelegate.get().addImmediateEvent(new ControllerServiceFindingEvent(this, JConfiguration.get()));
	}
	
	@Override
	protected ControllerFindingTriggerService doGetService() {
		return this;
	}
	
	
}
