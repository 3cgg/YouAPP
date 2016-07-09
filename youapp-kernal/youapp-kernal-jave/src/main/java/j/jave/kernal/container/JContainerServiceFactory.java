package j.jave.kernal.container;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

public class JContainerServiceFactory extends JServiceFactorySupport<JContainerService> {

	@Override
	protected JContainerService doGetService() {
		return JContainerService.get();
	}
}
