package test.j.jave.kernal.memcached;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.memcached.JDefaultMemcachedDisService;
import junit.framework.TestCase;

public class TestMemcache extends TestCase {
	
	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();
	{
		serviceFactoryManager.registerAllServices();
	}
	protected JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	
	public void testMemcache(){
		
		try{
			JDefaultMemcachedDisService defaultMemcachedDisService= serviceHubDelegate.getService(this, JDefaultMemcachedDisService.class);
			defaultMemcachedDisService.putNeverExpired("A", "TEST-A-B");
			String val=(String) defaultMemcachedDisService.get("A");
			System.out.println(val);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
