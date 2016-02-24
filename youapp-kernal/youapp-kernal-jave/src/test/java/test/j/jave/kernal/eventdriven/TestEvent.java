package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceHubMonitorEvent;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceMonitorEvent;
import j.jave.kernal.security.service.JMD5CipherService;

public class TestEvent  extends TestEventSupport{
	
	public void testUserFind(){
		Object object=JServiceHubDelegate.get().addImmediateEvent(new UserFindEvent(this, "jiaz"));
		System.out.println(object);
	}
	
	public void testHubMeta(){
		Object object=JServiceHubDelegate.get().addImmediateEvent(new JServiceHubMonitorEvent(this));
		System.out.println(object);
	}
	
	public void testServiceMeta(){
		Object object=JServiceHubDelegate.get().addImmediateEvent(new JServiceMonitorEvent(this,TestUserService.class,JMD5CipherService.class));
		System.out.println(object);
	}
	
	public void testMD5(){
		
		JMD5CipherService md5CipherService= serviceHubDelegate.getService(this, JMD5CipherService.class);
		
		String encrp=md5CipherService.encrypt("abc.def.ghi");
		System.out.println(encrp);
	}
	
}
