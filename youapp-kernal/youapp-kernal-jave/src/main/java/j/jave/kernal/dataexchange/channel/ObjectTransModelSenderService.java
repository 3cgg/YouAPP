package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

public class ObjectTransModelSenderService
extends JServiceFactorySupport<ObjectTransModelSenderService>
implements JService,ObjectTransModelSendingListener {
	
	@Override
	public Object trigger(ObjectTransModelSendingEvent event) {
		DefaultResponseFuture responseFuture=(DefaultResponseFuture) event.getResponseFuture();
		try{
			ObjectTransModelMessage message=(ObjectTransModelMessage) responseFuture.getRequest();
			Sender sender=new HttpClientSenderSender();
			ObjectTransModelMessage response=sender.send(message);
			responseFuture.setResponse(response);
		}catch(Exception e){
			responseFuture.setResponse(e);
		}
		return true;
	}
	
	
}
