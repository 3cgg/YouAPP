package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.eventdriven.servicehub.JEventExecution;
import j.jave.kernal.eventdriven.servicehub.JEventQueueEventExecutingPipe;

public class ControllerPromiseQueueExecutingPipe extends JEventQueueEventExecutingPipe {

	@Override
	protected boolean isLaterProcess0(JEventExecution execution) {
		ControllerCallPromiseEvent callPromiseEvent= (ControllerCallPromiseEvent) execution.getEvent();
		return !callPromiseEvent.getCallPromise().isDone();
	}
	
	
}
