package j.jave.kernal.container;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

public class JContainerServiceFactory extends JServiceFactorySupport<JContainerService> {

	private JContainerService instance=new JContainerService();
	
	@Override
	protected JContainerService doGetService() {
		return instance;
	}
	
	@Override
	public Class<?> getServiceImplClass() {
		return JContainerService.class;
	}
}
