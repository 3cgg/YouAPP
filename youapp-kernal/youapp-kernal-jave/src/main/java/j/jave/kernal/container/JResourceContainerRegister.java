package j.jave.kernal.container;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.listener.JServiceHubInitializedEvent;
import j.jave.kernal.eventdriven.servicehub.listener.JServiceHubInitializedListener;
import j.jave.kernal.jave.service.JService;

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
