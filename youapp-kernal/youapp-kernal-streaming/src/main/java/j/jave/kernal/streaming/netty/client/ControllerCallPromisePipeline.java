package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.eventdriven.servicehub.JEventQueuePipeline;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.jave.utils.JStringUtils;

public class ControllerCallPromisePipeline extends JEventQueuePipeline {

	public ControllerCallPromisePipeline(String name) {
		super(JStringUtils.isNotNullOrEmpty(name)?"-Controller-CallPromise":name);
	}
	
	@Override
	protected void initPipeline() {
		int order=-1; 
		register(ControllerPromiseQueueExecutingPipe.class, ++order);
		register(ControllerPromiseQueueCallBackPipe.class, ++order);
		register(JEventQueueEndPipe.class, ++order);
	}
	
	@Override
	public void addAPPEvent(JYouAPPEvent<?> appEvent) {
		super.addAPPEvent(appEvent);
	}
	
}
