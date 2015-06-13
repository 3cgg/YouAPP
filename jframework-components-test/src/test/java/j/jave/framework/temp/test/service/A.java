package j.jave.framework.temp.test.service;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import j.jave.framework.servicehub.JAsyncCallback;
import j.jave.framework.servicehub.JEventExecution;
import j.jave.framework.servicehub.JServiceHubDelegate;

public class A {

	/**
	 * execute the event sent by {@link #daemon} thread. the thread is actual executor.
	 */
	private static ScheduledThreadPoolExecutor eventExecutor=new ScheduledThreadPoolExecutor(10);
	
	public static void main(String[] args) throws InterruptedException {
		final A a=new A();
		final JServiceHubDelegate serviceHubDelegate= JServiceHubDelegate.get();
		serviceHubDelegate.register(a, TestService.class, new TestServiceFactory());
		
		while(true){
			for(int i=0;i<100;i++){
				eventExecutor.execute(new Runnable() {
					
					@Override
					public void run() {
						serviceHubDelegate.addDelayEvent(new TestServicePrintEvent(a),
								new JAsyncCallback() {
							@Override
							public void callback(Object[] result, JEventExecution eventExecution) {
								System.out.println("111111111---result --->: "+(result.length>0?result[0]:null)+Thread.currentThread().getId()+" ----NAM----"+Thread.currentThread().getName());
							}
						});
						
						TestServicePrintEvent event=new TestServicePrintEvent(a);
						event.setAsyncCallback(new JAsyncCallback() {
							
							@Override
							public void callback(Object[] result, JEventExecution eventExecution) {
								System.out.println("222222222 ---result --->: "+(result.length>0?result[0]:null)+Thread.currentThread().getId()+" ----NAM----"+Thread.currentThread().getName());
								
							}
						});
						serviceHubDelegate.addDelayEvent(event);
					}
				});
			}
			
			Thread.sleep(15*1000);
		}
		
	}
	
}
