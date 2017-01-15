package test.j.jave.kernal.ehcache;

import j.jave.kernal.ehcache.JDefaultEhcacheService;
import junit.framework.TestCase;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

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
