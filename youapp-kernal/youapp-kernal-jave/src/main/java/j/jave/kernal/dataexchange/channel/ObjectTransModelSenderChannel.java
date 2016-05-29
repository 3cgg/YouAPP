package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

public class ObjectTransModelSenderChannel 
implements ExchangeChannel<ObjectTransModelMessage> {

	
	@Override
	public final ResponseFuture write(ObjectTransModelMessage objectTransModelMessage) throws Exception {
		ResponseFuture responseFuture=new DefaultResponseFuture(this);
		JServiceHubDelegate.get().addDelayEvent(new ObjectTransModelSendingEvent(this, responseFuture));
		return responseFuture;
	}
	
	@Override
	public Object read() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
