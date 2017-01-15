package me.bunny.kernel.jave.sync;

import me.bunny.kernel.eventdriven.servicehub.JEventQueueEventExecutingPipe;
import me.bunny.kernel.eventdriven.servicehub.JEventQueueEventResultCallBackPipe;
import me.bunny.kernel.eventdriven.servicehub.JEventQueuePipeline;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;
import me.bunny.kernel.jave.utils.JStringUtils;

public class JSyncEventQueuePipeline extends JEventQueuePipeline {

	public JSyncEventQueuePipeline(String name) {
		super(JStringUtils.isNotNullOrEmpty(name)?"-Sync-Monitor":name);
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
