package j.jave.kernal.container;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

public class JContainerServiceFactory extends JServiceFactorySupport<JContainerService> {

	@Override
	public JContainerService getService() {
		return JContainerService.get();
	}
}
