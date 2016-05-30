package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

public class MessageChannel 
implements Channel<Message> {

	
	@Override
	public final ResponseFuture write(Message message) throws Exception {
		DefaultResponseFuture responseFuture=new DefaultResponseFuture(this);
		responseFuture.setRequest(message);
		MessageSendingEvent event=new MessageSendingEvent(this, responseFuture);
		event.setGetResultLater(true);
		responseFuture.setEvent(event);
		JServiceHubDelegate.get().addDelayEvent(event);
		return responseFuture;
	}
	
	@Override
	public Object read() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
