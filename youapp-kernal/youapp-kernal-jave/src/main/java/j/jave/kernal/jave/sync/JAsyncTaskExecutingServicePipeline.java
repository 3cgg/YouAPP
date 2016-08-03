package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JEventQueueEventExecutingPipe;
import j.jave.kernal.eventdriven.servicehub.JEventQueueEventResultCallBackPipe;
import j.jave.kernal.eventdriven.servicehub.JEventQueuePipeline;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;

public class JAsyncTaskExecutingServicePipeline extends JEventQueuePipeline {

	public JAsyncTaskExecutingServicePipeline(String name) {
		super("-Sync-Executing-Service");
	}
	
	@Override
	protected void initPipeline() {
		int order=-1; 
		register(JEventQueueEventExecutingPipe.class, ++order);
		register(JEventQueueEventResultCallBackPipe.class, ++order);
		register(JEventQueueEndPipe.class, ++order);
	}
	
	@Override
	public void addAPPEvent(JYouAPPEvent<?> appEvent) {
		super.addAPPEvent(appEvent);
	}
	
}
