package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

import org.junit.Before;

public class TestEventSupport{

	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();

	protected JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	@Before
	public void initialize() throws Exception {
		serviceFactoryManager.registerAllServices();
	}
	
}
