package j.jave.kernal.streaming.netty.client;

import me.bunny.kernel.eventdriven.servicehub.JEventExecution;
import me.bunny.kernel.eventdriven.servicehub.JEventQueueEventExecutingPipe;

public class ControllerPromiseQueueExecutingPipe extends JEventQueueEventExecutingPipe {

	@Override
	protected boolean isLaterProcess0(JEventExecution execution) {
		ControllerCallPromiseEvent callPromiseEvent= (ControllerCallPromiseEvent) execution.getEvent();
		return !callPromiseEvent.getCallPromise().isDone();
	}
	
	
}
