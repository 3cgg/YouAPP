package j.jave.kernal.streaming.netty.client;

import j.jave.kernal.eventdriven.servicehub.JEventExecution;
import j.jave.kernal.eventdriven.servicehub.JEventQueueEventResultCallBackPipe;

public class ControllerPromiseQueueCallBackPipe extends JEventQueueEventResultCallBackPipe {
	
	@Override
	protected boolean isLaterProcess0(JEventExecution execution) {
		ControllerCallPromiseEvent callPromiseEvent= (ControllerCallPromiseEvent) execution.getEvent();
		return !callPromiseEvent.getCallPromise().isDone();
	}
	
	
}
