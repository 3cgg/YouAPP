package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.dataexchange.channel.ObjectTransModelMessage;
import j.jave.kernal.dataexchange.channel.ObjectTransModelSenderChannel;
import j.jave.kernal.dataexchange.channel.ResponseFuture;
import j.jave.kernal.dataexchange.exception.JDataExchangeException;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

public abstract class JProtocolSender {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	private JProtocolByteHandler protocolByteHandler;
	
	protected final Object data;
	
	protected String url;
	
	protected JProtocol protocol; 
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	public JProtocolSender(Object data) {
		super();
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public <T> T send() {
		try{
			byte[] bytes=doSend();
			if(protocolByteHandler!=null){
				return (T) protocolByteHandler.handle(bytes);
			}
			else{
				return (T) new String(bytes,"UTF-8");
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JDataExchangeException(e);
		}
	}

	protected abstract byte[] doSend() throws Exception;
	
	static class ObjectProtocolSender extends JProtocolSender{
		
		public ObjectProtocolSender(JObjectTransModel objectTransModel) {
			super(objectTransModel);
		}

		@Override
		protected byte[] doSend()  throws Exception{
			
			ObjectTransModelMessage message=new ObjectTransModelMessage();
			message.setUrl(this.url);
			message.setProtocol(this.protocol);
			message.setData(base64Service.encodeBase64String(JObjectSerializableUtils.serializeObject(data)));
			
			byte[] bytes=null;
			ResponseFuture responseFuture= new ObjectTransModelSenderChannel().write(message);
			if(responseFuture.await()!=null){
				ObjectTransModelMessage modelMessage=(ObjectTransModelMessage) responseFuture.getResponse();
				String data64=modelMessage.getData();
				bytes= base64Service.decodeBase64(data64);
			}
			return bytes;
		}
		
	}
	
	
	static class JSONProtocolSender extends JProtocolSender{
		
		public JSONProtocolSender(JObjectTransModel objectTransModel) {
			super(objectTransModel);
		}

		@Override
		protected byte[] doSend()  throws Exception{
			ObjectTransModelMessage message=new ObjectTransModelMessage();
			message.setUrl(this.url);
			message.setProtocol(this.protocol);
			
			String objectJSON=JJSON.get().formatObject(data);
			message.setData(base64Service.encodeBase64String(objectJSON.getBytes("utf-8")));
			byte[] bytes=null;
			ResponseFuture responseFuture= new ObjectTransModelSenderChannel().write(message);
			if(responseFuture.await()!=null){
				ObjectTransModelMessage modelMessage=(ObjectTransModelMessage) responseFuture.getResponse();
				String data64=modelMessage.getData();
				bytes= base64Service.decodeBase64(data64);
			}
			return bytes;
		}
		
	}
	
	JProtocolSender setProtocolByteHandler(JProtocolByteHandler protocolByteHandler) {
		this.protocolByteHandler = protocolByteHandler;
		return this;
	}
	
	
	public JProtocolSender setProtocol(JProtocol protocol) {
		this.protocol = protocol;
		return this;
	}
	
	public JProtocolSender setUrl(String url) {
		this.url = url;
		return this;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
