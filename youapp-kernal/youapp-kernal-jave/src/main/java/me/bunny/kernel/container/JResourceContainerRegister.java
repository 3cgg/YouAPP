package me.bunny.kernel.container;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.listener.JServiceHubInitializedEvent;
import me.bunny.kernel.eventdriven.servicehub.listener.JServiceHubInitializedListener;
import me.bunny.kernel.jave.service.JService;

public class JResourceContainerRegister
extends JServiceFactorySupport<JResourceContainerRegister>
implements JService , JServiceHubInitializedListener
{
	
	@Override
	public Object trigger(JServiceHubInitializedEvent event) {
		JResourceContainerConfig defaultConfig=new JResourceContainerConfig();
		JResourceContainer resourceContainer=new JResourceContainer(defaultConfig);
		resourceContainer.initialize();
		return true;
	}
	
	@Override
	protected JResourceContainerRegister doGetService() {
		return this;
	}
	
}
