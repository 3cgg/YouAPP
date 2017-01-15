package test.j.jave.kernal.eventdriven;

import org.junit.Test;

import me.bunny.kernel.eventdriven.servicehub.EventExecutionResult;
import me.bunny.kernel.eventdriven.servicehub.JAsyncCallback;
import me.bunny.kernel.eventdriven.servicehub.JEventExecution;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.eventdriven.servicehub.listener.JServiceExistsEvent;
import me.bunny.kernel.eventdriven.servicehub.monitor.JEventProcessingStatus;
import me.bunny.kernel.eventdriven.servicehub.monitor.JServiceHubMonitorEvent;
import me.bunny.kernel.eventdriven.servicehub.monitor.JServiceMonitorEvent;
import me.bunny.kernel.eventdriven.servicehub.monitor.JServiceMonitorService;
import me.bunny.kernel.jave.service.JService;
import me.bunny.kernel.jave.support.JDefaultHashCacheService;
import me.bunny.kernel.security.service.JMD5CipherService;

public class TestEvent  extends TestEventSupport{
	
	public void testUserFind(){
		System.out.println("BEGIN");
		for(int i=0;i<100000;i++){
			final String istr=""+i;
			JServiceHubDelegate.get().addDelayEvent(new UserFindEvent(this, "jiaz : "+i),new JAsyncCallback() {
				
				@Override
				public void callback(EventExecutionResult result, JEventExecution eventExecution) {
					System.out.println("-------===---"+istr+"-----"+result);
				}
			});
		}
		System.out.println("M");
		
		for(int i=0;i<100000;i++){
			final String istr=""+i;
			JServiceHubDelegate.get().addDelayEvent(new UserFindEvent(this, "jiaz : "+i),new JAsyncCallback() {
				
				@Override
				public void callback(EventExecutionResult result, JEventExecution eventExecution) {
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
	
	@Test
	public void testMD5(){
		JMD5CipherService md5CipherService= serviceHubDelegate.getService(this, JMD5CipherService.class);
		
		TestUserService testUserService= serviceHubDelegate.getService(this,TestUserService.class);
		System.out.println(testUserService.describer());
		String encrp=md5CipherService.encrypt("abc.def.ghi");
		System.out.println(encrp);
		
	}
	
	public void testServiceExists(){
		Object object=JServiceHubDelegate.get().
				addImmediateEvent(new JServiceExistsEvent(this,JService.class));
		System.out.println(object);
		
		object=JServiceHubDelegate.get().
				addImmediateEvent(new JServiceExistsEvent(this,JMD5CipherService.class));
		System.out.println(object);
		
		object=JServiceHubDelegate.get().
				existsService(JService.class);
		System.out.println(object);
		
		object=JServiceHubDelegate.get().
				existsService(JMD5CipherService.class);
		System.out.println(object);
		
	}
	
	@Test
	public void testServiceMonitorService(){
		JServiceHubMonitorEvent serviceHubMonitorEvent=new JServiceHubMonitorEvent(this);
		Object object=JServiceHubDelegate.get().addImmediateEvent(serviceHubMonitorEvent);
		System.out.println(object);
		
		JServiceHubMonitorEvent serviceHubMonitorEvent2=new JServiceHubMonitorEvent(this);
		serviceHubMonitorEvent2.addAsyncCallback(new JAsyncCallback() {
			@Override
			public void callback(EventExecutionResult result, JEventExecution eventExecution) {
				System.out.println("---------"+eventExecution);
			}
		});
		serviceHubMonitorEvent2.setGetResultLater(true);
		JServiceHubDelegate.get().addDelayEvent(serviceHubMonitorEvent2);
		System.out.println(object);
		
		object=JServiceHubDelegate.get().getResultByEventId(serviceHubMonitorEvent2.getUnique());
		System.out.println(object);
		
		JServiceMonitorService serviceMonitorService= serviceHubDelegate.getService(this,JServiceMonitorService.class);
		object=serviceMonitorService.getServiceHubMeta();
		System.out.println(object);
		
		object=serviceMonitorService.getServiceRuntimeMetas(JServiceMonitorService.class);
		System.out.println(object);
	
		JEventProcessingStatus eventProcessingStatus=serviceMonitorService.getEventProcessingStatus(serviceHubMonitorEvent.getUnique());
		
		System.out.println(eventProcessingStatus);
		
	}
	
	@Test
	public void testHashCache(){
		
		JDefaultHashCacheService cacheService=
				serviceHubDelegate.getService(this, JDefaultHashCacheService.class);
		
		cacheService.putNeverExpired("s", "s");
		System.out.println("end");
	}
	
}
