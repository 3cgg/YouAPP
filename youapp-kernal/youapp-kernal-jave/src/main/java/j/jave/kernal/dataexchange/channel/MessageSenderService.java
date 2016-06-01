package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionException;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.service.JService;

public class MessageSenderService
extends JServiceFactorySupport<MessageSenderService>
implements JService,MessageSendingListener {
	
	private final static JLogger LOGGER=JLoggerFactory.getLogger(MessageSenderService.class);
	
	
	private Sender sender;
	
	private final Object sync=new Object();
	
	public Sender getSender() {
		if(sender==null){
			synchronized (sync) {
				if(sender==null){
					String senderClassStr=JConfiguration.get().getString(JProperties.SERVICE_CHANNEL_DATAE_XCHANGE_SENDER, HttpClientSender.class.getName());
					try{
						sender=(Sender) JClassUtils.load(senderClassStr).newInstance();
					}catch(Exception e){
						LOGGER.info("the sender["+senderClassStr+"] cannot be initialized,"+e.getMessage());
						sender=new HttpClientSender();
						LOGGER.info("the sender["+HttpClientSender.class.getName()+"] be as default.");
					}
				}
			}
		}
		return sender;
	}
	
	@Override
	public Object trigger(MessageSendingEvent event) {
		DefaultResponseFuture responseFuture=(DefaultResponseFuture) event.getResponseFuture();
		Message response=null;
		try{
			Message message=(Message) responseFuture.getRequest();
			response=getSender().send(message);
		}catch(Exception e){
			throw new JEventExecutionException(e);
		}
		return response;
	}
	
	
	@Override
	public MessageSenderService getService() {
		return this;
	}
	
}
