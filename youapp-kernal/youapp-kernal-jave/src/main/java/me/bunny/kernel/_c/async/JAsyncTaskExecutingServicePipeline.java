package me.bunny.kernel._c.async;

import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.eventdriven.servicehub.JEventQueueEventExecutingPipe;
import me.bunny.kernel.eventdriven.servicehub.JEventQueueEventResultCallBackPipe;
import me.bunny.kernel.eventdriven.servicehub.JEventQueuePipeline;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

public class JAsyncTaskExecutingServicePipeline extends JEventQueuePipeline {

	public JAsyncTaskExecutingServicePipeline(String name) {
		super(JStringUtils.isNotNullOrEmpty(name)?"-Sync-Executing-Service":name);
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
