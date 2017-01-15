package me.bunny.kernel.dataexchange.channel;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.servicehub.JEventExecutionException;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class JMessageSenderService
extends JServiceFactorySupport<JMessageSenderService>
implements JService,JMessageSendingListener {
	
	private final static JLogger LOGGER=JLoggerFactory.getLogger(JMessageSenderService.class);
	
	
	private JDirectSender sender;
	
	private final Object sync=new Object();
	
	public JDirectSender getSender() {
		if(sender==null){
			synchronized (sync) {
				if(sender==null){
					String senderClassStr=JConfiguration.get().getString(JProperties.SERVICE_CHANNEL_DATAE_XCHANGE_SENDER, JHttpClientDirectSender.class.getName());
					try{
						sender=(JDirectSender) JClassUtils.load(senderClassStr).newInstance();
					}catch(Exception e){
						LOGGER.info("the sender["+senderClassStr+"] cannot be initialized,"+e.getMessage());
						sender=new JHttpClientDirectSender();
						LOGGER.info("the sender["+JHttpClientDirectSender.class.getName()+"] be as default.");
					}
				}
			}
		}
		return sender;
	}
	
	@Override
	public Object trigger(JMessageSendingEvent event) {
		JDefaultResponseFuture responseFuture=(JDefaultResponseFuture) event.getResponseFuture();
		JMessage response=null;
		try{
			JMessage message=(JMessage) responseFuture.getRequest();
			response=getSender().send(message);
		}catch(Exception e){
			throw new JEventExecutionException(e);
		}
		return response;
	}
	
	
	@Override
	public JMessageSenderService doGetService() {
		return this;
	}
	
}
