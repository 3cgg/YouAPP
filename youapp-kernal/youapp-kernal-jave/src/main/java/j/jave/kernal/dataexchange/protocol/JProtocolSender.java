package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.dataexchange.channel.ObjectTransModelMessage;
import j.jave.kernal.dataexchange.channel.ObjectTransModelSenderChannel;
import j.jave.kernal.dataexchange.channel.ResponseFuture;
import j.jave.kernal.dataexchange.exception.JDataExchangeException;
import j.jave.kernal.http.JResponseHandler;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

public abstract class JProtocolSender {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	private JProtocolResultHandler protocolResultHandler;
	
	protected final JObjectTransModel objectTransModel;
	
	protected ObjectTransModelSenderChannel senderChannel;
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	public JProtocolSender(JObjectTransModel objectTransModel) {
		super();
		this.objectTransModel = objectTransModel;
	}

	@SuppressWarnings("unchecked")
	public <T> T send() {
		try{
			byte[] bytes=doSend();
			if(protocolResultHandler!=null){
				return (T) protocolResultHandler.handle(bytes);
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
	
	private static JResponseHandler<byte[]> responseHandler=new JResponseHandler<byte[]>(){
		public byte[] process(byte[] bytes) throws JResponseHandler.ProcessException {
			return bytes;
		};
	};
	
	static class ObjectProtocolSender extends JProtocolSender{
		
		public ObjectProtocolSender(JObjectTransModel objectTransModel) {
			super(objectTransModel);
		}

		@Override
		protected byte[] doSend()  throws Exception{
			
			ObjectTransModelMessage message=new ObjectTransModelMessage();
			message.setUrl(objectTransModel.getUrl());
			message.setProtocol(objectTransModel.getProtocol());
			message.setData(base64Service.encodeBase64String(JObjectSerializableUtils.serializeObject(objectTransModel.getObjectWrappers())));
			
			byte[] bytes=null;
			ResponseFuture responseFuture= new ObjectTransModelSenderChannel().write(message);
			while(responseFuture.await()!=null){
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
			message.setUrl(objectTransModel.getUrl());
			message.setProtocol(objectTransModel.getProtocol());
			
			String objectJSON=JJSON.get().formatObject(objectTransModel.getObjectWrappers());
			message.setData(base64Service.encodeBase64String(objectJSON.getBytes("utf-8")));
			byte[] bytes=null;
			ResponseFuture responseFuture= new ObjectTransModelSenderChannel().write(message);
			while(responseFuture.await()!=null){
				ObjectTransModelMessage modelMessage=(ObjectTransModelMessage) responseFuture.getResponse();
				String data64=modelMessage.getData();
				bytes= base64Service.decodeBase64(data64);
			}
			return bytes;
		}
		
	}
	
	void setProtocolResultHandler(
			JProtocolResultHandler protocolResultHandler) {
		this.protocolResultHandler = protocolResultHandler;
	}
	
}
