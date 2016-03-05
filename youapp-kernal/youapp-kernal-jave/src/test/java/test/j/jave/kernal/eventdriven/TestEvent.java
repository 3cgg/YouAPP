package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAsyncCallback;
import j.jave.kernal.eventdriven.servicehub.JEventExecution;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceHubMonitorEvent;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceMonitorEvent;
import j.jave.kernal.security.service.JMD5CipherService;

public class TestEvent  extends TestEventSupport{
	
	public void testUserFind(){
		System.out.println("BEGIN");
		for(int i=0;i<100000;i++){
			final String istr=""+i;
			JServiceHubDelegate.get().addDelayEvent(new UserFindEvent(this, "jiaz : "+i),new JAsyncCallback() {
				
				@Override
				public void callback(Object[] result, JEventExecution eventExecution) {
					System.out.println("-------===---"+istr+"-----"+result[0]);
				}
			});
		}
		System.out.println("M");
		
		for(int i=0;i<100000;i++){
			final String istr=""+i;
			JServiceHubDelegate.get().addDelayEvent(new UserFindEvent(this, "jiaz : "+i),new JAsyncCallback() {
				
				@Override
				public void callback(Object[] result, JEventExecution eventExecution) {
					System.out.println("-------===---"+istr+"-----"+result);
				}
			});
		}
		
		System.out.println("E");
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
		
		TestUserService testUserService= serviceHubDelegate.getService(this,TestUserService.class);
		System.out.println(testUserService.describer());
		String encrp=md5CipherService.encrypt("abc.def.ghi");
		System.out.println(encrp);
	}
	
}
