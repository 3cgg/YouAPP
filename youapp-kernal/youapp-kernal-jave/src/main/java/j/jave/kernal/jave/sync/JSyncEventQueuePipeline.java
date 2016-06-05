package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JEventQueueEventExecutingPipe;
import j.jave.kernal.eventdriven.servicehub.JEventQueuePipeline;

public class JSyncEventQueuePipeline extends JEventQueuePipeline {

	public JSyncEventQueuePipeline(String name) {
		super("Sync-Monitor");
	}
	
	@Override
	protected void initPipeline() {
		int order=-1; 
		register(JEventQueueEventExecutingPipe.class, ++order);
		register(JEventQueueEndPipe.class, ++order);
	}
	
	@Override
	public void addAPPEvent(JAPPEvent<?> appEvent) {
		super.addAPPEvent(appEvent);
	}
	
}
