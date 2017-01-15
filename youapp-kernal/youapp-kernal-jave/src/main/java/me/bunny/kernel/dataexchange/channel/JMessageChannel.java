package me.bunny.kernel.dataexchange.channel;

import java.util.concurrent.atomic.AtomicBoolean;

import me.bunny.kernel.eventdriven.servicehub.EventExecutionResult;
import me.bunny.kernel.eventdriven.servicehub.JAsyncCallback;
import me.bunny.kernel.eventdriven.servicehub.JEventExecution;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.utils.JCollectionUtils;

public class JMessageChannel 
implements JChannel<JMessage> {

	
	@Override
	public final JResponseFuture write(JMessage message) throws Exception {
		final JDefaultResponseFuture responseFuture=new JDefaultResponseFuture(this);
		responseFuture.setRequest(message);
		JMessageSendingEvent event=new JMessageSendingEvent(this, responseFuture);
//		event.setGetResultLater(true);
		responseFuture.setEvent(event);
		JServiceHubDelegate.get().addDelayEvent(event,new JAsyncCallback() {
			@Override
			public void callback(EventExecutionResult result,
					JEventExecution eventExecution) {
				if(result.getException()!=null){
					responseFuture.setException(result.getException());
				}
				Object[] objects=result.getObjects();
				if(JCollectionUtils.hasInArray(objects)){
					responseFuture.setResponse(objects[0]);
				}
				responseFuture.setComplete(new AtomicBoolean(true));
			}
		});
		return responseFuture;
	}
	
	@Override
	public Object read() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
