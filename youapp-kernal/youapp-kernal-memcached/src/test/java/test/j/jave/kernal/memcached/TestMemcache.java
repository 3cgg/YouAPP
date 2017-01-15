package test.j.jave.kernal.memcached;

import j.jave.kernal.memcached.JDefaultMemcachedDisServiceImpl;
import junit.framework.TestCase;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

public class TestMemcache extends TestCase {
	
	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();
	{
		serviceFactoryManager.registerAllServices();
	}
	protected JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	
	public void testMemcache(){
		
		try{
			JDefaultMemcachedDisServiceImpl defaultMemcachedDisService= serviceHubDelegate.getService(this, JDefaultMemcachedDisServiceImpl.class);
			defaultMemcachedDisService.putNeverExpired("A", "TEST-A-B");
			String val=(String) defaultMemcachedDisService.get("A");
			System.out.println(val);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
