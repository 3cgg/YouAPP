package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JEventQueueEventExecutingPipe;
import j.jave.kernal.eventdriven.servicehub.JEventQueueEventResultCallBackPipe;
import j.jave.kernal.eventdriven.servicehub.JEventQueuePipeline;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.jave.utils.JStringUtils;

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
