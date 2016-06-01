package j.jave.kernal.dataexchange.modelprotocol;

import j.jave.kernal.dataexchange.channel.Message;
import j.jave.kernal.dataexchange.channel.MessageChannel;
import j.jave.kernal.dataexchange.channel.ResponseFuture;
import j.jave.kernal.dataexchange.exception.JDataExchangeException;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

public class JDefaultProtocolSender {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	private JProtocolByteHandler receiveHandler;
	
	private JProtocolObjectHandler sendHandler;
	
	private String serverHandler;
	
	protected final Object data;
	
	protected String url;
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	public JDefaultProtocolSender(Object data) {
		super();
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public <T> T send() {
		try{
			byte[] bytes=doSend();
			if(receiveHandler!=null){
				return (T) receiveHandler.handle(bytes);
			}
			else{
				return (T) new String(bytes,"UTF-8");
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JDataExchangeException(e);
		}
	}

	protected byte[] doSend() throws Exception{

		Message message=new Message();
		message.setUrl(this.url);
		message.setHandler(serverHandler);
		message.setData(base64Service.encodeBase64String(sendHandler.handle(data)));
		byte[] bytes=null;
		ResponseFuture responseFuture= new MessageChannel().write(message);
		if(responseFuture.await()!=null){
			Message modelMessage=(Message) responseFuture.getResponse();
			String data64=modelMessage.getData();
			bytes= base64Service.decodeBase64(data64);
		}
		return bytes;
		
	}
	
	static class ObjectProtocolSender extends JDefaultProtocolSender{
		
		public ObjectProtocolSender(Object data) {
			super(data);
		}

		@Override
		protected byte[] doSend()  throws Exception{
			
			Message message=new Message();
			message.setUrl(this.url);
//			message.setProtocol(this.protocol);
			message.setData(base64Service.encodeBase64String(JObjectSerializableUtils.serializeObject(data)));
			
			byte[] bytes=null;
			ResponseFuture responseFuture= new MessageChannel().write(message);
			if(responseFuture.await()!=null){
				Message modelMessage=(Message) responseFuture.getResponse();
				String data64=modelMessage.getData();
				bytes= base64Service.decodeBase64(data64);
			}
			return bytes;
		}
		
	}
	
	
	static class JSONProtocolSender extends JDefaultProtocolSender{
		
		public JSONProtocolSender(Object data) {
			super(data);
		}

		@Override
		protected byte[] doSend()  throws Exception{
			Message message=new Message();
			message.setUrl(this.url);
//			message.setProtocol(this.protocol);
			
			String objectJSON=JJSON.get().formatObject(data);
			message.setData(base64Service.encodeBase64String(objectJSON.getBytes("utf-8")));
			byte[] bytes=null;
			ResponseFuture responseFuture= new MessageChannel().write(message);
			if(responseFuture.await()!=null){
				Message modelMessage=(Message) responseFuture.getResponse();
				String data64=modelMessage.getData();
				bytes= base64Service.decodeBase64(data64);
			}
			return bytes;
		}
		
	}
	
	public JDefaultProtocolSender setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public JDefaultProtocolSender setReceiveHandler(JProtocolByteHandler receiveHandler) {
		this.receiveHandler = receiveHandler;
		return this;
	}
	public JDefaultProtocolSender setSendHandler(JProtocolObjectHandler sendHandler) {
		this.sendHandler = sendHandler;
		return this;
	}
	public JDefaultProtocolSender setServerHandler(String serverHandler) {
		this.serverHandler = serverHandler;
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
