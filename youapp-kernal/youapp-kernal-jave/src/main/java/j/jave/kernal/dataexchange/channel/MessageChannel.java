package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.EventExecutionResult;
import j.jave.kernal.eventdriven.servicehub.JAsyncCallback;
import j.jave.kernal.eventdriven.servicehub.JEventExecution;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class MessageChannel 
implements Channel<Message> {

	
	@Override
	public final ResponseFuture write(Message message) throws Exception {
		final DefaultResponseFuture responseFuture=new DefaultResponseFuture(this);
		responseFuture.setRequest(message);
		MessageSendingEvent event=new MessageSendingEvent(this, responseFuture);
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
