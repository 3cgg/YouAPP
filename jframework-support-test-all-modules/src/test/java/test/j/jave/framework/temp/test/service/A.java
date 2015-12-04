package test.j.jave.framework.temp.test.service;

import j.jave.framework.commons.eventdriven.context.JCommonsEventDrivenContext;
import j.jave.framework.commons.eventdriven.context.JCommonsEventDrivenContext.EventQueuePipeProvider;
import j.jave.framework.commons.eventdriven.servicehub.JAsyncCallback;
import j.jave.framework.commons.eventdriven.servicehub.JEventExecution;
import j.jave.framework.commons.eventdriven.servicehub.JEventQueuePipe;
import j.jave.framework.commons.eventdriven.servicehub.JEventQueuePipes.JEventQueuePipeInfo;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class A {

	/**
	 * execute the event sent by {@link #daemon} thread. the thread is actual executor.
	 */
	private static ScheduledThreadPoolExecutor eventExecutor=new ScheduledThreadPoolExecutor(10);
	
	public static class B extends JEventQueuePipe{
		@Override
		public void addEventExecution(JEventExecution eventExecution) {
			System.out.println(" in B...............");
			handoff(eventExecution);
		}
	} 
	
	
	public static class C extends JEventQueuePipe{
		@Override
		public void addEventExecution(JEventExecution eventExecution) {
			System.out.println(" in C...............");
			handoff(eventExecution);
		}
	} 
	
	public static void main(String[] args) throws InterruptedException {
		
	
		EventQueuePipeProvider eventQueuePipeProvider=  JCommonsEventDrivenContext.get().getEventQueuePipeProvider();
		eventQueuePipeProvider.addEventQueuePipe(new JEventQueuePipeInfo(C.class, 2));
		eventQueuePipeProvider.addEventQueuePipe(new JEventQueuePipeInfo(B.class, 1));
		
		
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
