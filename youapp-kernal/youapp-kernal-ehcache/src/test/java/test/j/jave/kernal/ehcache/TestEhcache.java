package test.j.jave.kernal.ehcache;

import j.jave.kernal.ehcache.JDefaultEhcacheService;
import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import junit.framework.TestCase;

public class TestEhcache extends TestCase {
	
	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();
	{
		serviceFactoryManager.registerAllServices();
	}
	protected JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	
	public void testEhcache(){
		
		try{
			JDefaultEhcacheService defaultEhcacheService= serviceHubDelegate.getService(this, JDefaultEhcacheService.class);
			defaultEhcacheService.putNeverExpired("A", "TEST-A-B");
			String val=(String) defaultEhcacheService.get("A");
			System.out.println(val);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
