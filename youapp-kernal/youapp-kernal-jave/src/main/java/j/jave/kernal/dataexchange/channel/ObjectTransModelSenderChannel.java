package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

public class ObjectTransModelSenderChannel 
implements ExchangeChannel<ObjectTransModelMessage> {

	
	@Override
	public final ResponseFuture write(ObjectTransModelMessage objectTransModelMessage) throws Exception {
		DefaultResponseFuture responseFuture=new DefaultResponseFuture(this);
		ObjectTransModelSendingEvent event=new ObjectTransModelSendingEvent(this, responseFuture);
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
