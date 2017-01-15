package j.jave.kernal.streaming.netty.client;

import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.eventdriven.servicehub.JEventQueuePipeline;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

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
