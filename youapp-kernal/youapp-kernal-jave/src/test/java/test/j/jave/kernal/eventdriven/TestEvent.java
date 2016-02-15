package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import junit.framework.TestCase;

public class TestEvent  extends TestCase{

	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();
	{
		serviceFactoryManager.registerAllServices();
	}
	
	public void testUserFind(){
		Object object=JServiceHubDelegate.get().addImmediateEvent(new UserFindEvent(this, "jiaz"));
		System.out.println(object);
		
	}
	
}
