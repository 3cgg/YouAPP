package test.j.jave.web.htmlclient;

import org.junit.Before;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

public class TestEventSupport{

	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();

	protected JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	@Before
	public void initialize() throws Exception {
		serviceFactoryManager.registerAllServices();
	}
	
}
