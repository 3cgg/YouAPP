package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionException;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.service.JService;

public class ObjectTransModelSenderService
extends JServiceFactorySupport<ObjectTransModelSenderService>
implements JService,ObjectTransModelSendingListener {
	
	private final static JLogger LOGGER=JLoggerFactory.getLogger(ObjectTransModelSenderService.class);
	
	
	private Sender sender;
	
	private final Object sync=new Object();
	
	public Sender getSender() {
		if(sender==null){
			synchronized (sync) {
				if(sender==null){
					String senderClassStr=JConfiguration.get().getString(JProperties.SERVICE_CHANNEL_DATAE_XCHANGE_SENDER, HttpClientSenderSender.class.getName());
					try{
						sender=(Sender) JClassUtils.load(senderClassStr).newInstance();
					}catch(Exception e){
						LOGGER.info("the sender["+senderClassStr+"] cannot be initialized,"+e.getMessage());
						sender=new HttpClientSenderSender();
						LOGGER.info("the sender["+HttpClientSenderSender.class.getName()+"] be as default.");
					}
				}
			}
		}
		return sender;
	}
	
	@Override
	public Object trigger(ObjectTransModelSendingEvent event) {
		DefaultResponseFuture responseFuture=(DefaultResponseFuture) event.getResponseFuture();
		ObjectTransModelMessage response=null;
		try{
			ObjectTransModelMessage message=(ObjectTransModelMessage) responseFuture.getRequest();
			Sender sender=getSender();
			response=sender.send(message);
		}catch(Exception e){
			throw new JEventExecutionException(e);
		}
		return response;
	}
	
	
	@Override
	public ObjectTransModelSenderService getService() {
		return this;
	}
	
}
