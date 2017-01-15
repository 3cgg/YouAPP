package me.bunny.modular._p.streaming.netty.client;

import me.bunny.kernel.eventdriven.servicehub.JEventExecution;
import me.bunny.kernel.eventdriven.servicehub.JEventQueueEventResultCallBackPipe;

public class ControllerPromiseQueueCallBackPipe extends JEventQueueEventResultCallBackPipe {
	
	@Override
	protected boolean isLaterProcess0(JEventExecution execution) {
		ControllerCallPromiseEvent callPromiseEvent= (ControllerCallPromiseEvent) execution.getEvent();
		return !callPromiseEvent.getCallPromise().isDone();
	}
	
	
}
