package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.dataexchange.exception.JDataExchangeException;
import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.http.JResponseHandler;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

import java.io.IOException;

public abstract class JProtocolSender {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	private JProtocolResultHandler protocolResultHandler;
	
	protected final JObjectTransModel objectTransModel;
	
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

	protected abstract byte[] doSend() throws IOException;
	
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
		protected byte[] doSend()  throws IOException{
			byte[] bytes=JObjectSerializableUtils.serializeObject(objectTransModel);
			return (byte[]) JHttpFactoryProvider.getHttpFactory().getHttpPost()
			.setUrl(objectTransModel.getUrl())
			.setEntry(bytes)
			.setResponseHandler(responseHandler)
			.putHead(JProtocolConstants.PROTOCOL_HEAD, objectTransModel.getSendProtocol().name())
			.execute();
		}
		
	}
	
	
	static class JSONProtocolSender extends JProtocolSender{
		
		public JSONProtocolSender(JObjectTransModel objectTransModel) {
			super(objectTransModel);
		}

		@Override
		protected byte[] doSend()  throws IOException{
			String objectJSON=JJSON.get().formatObject(objectTransModel);
			return (byte[]) JHttpFactoryProvider.getHttpFactory().getHttpPost()
			.setUrl(objectTransModel.getUrl())
			.setEntry(objectJSON.getBytes("utf-8"))
			.setResponseHandler(responseHandler)
			.putHead(JProtocolConstants.PROTOCOL_HEAD, objectTransModel.getSendProtocol().name())
			.execute();
		}
		
	}
	
	void setProtocolResultHandler(
			JProtocolResultHandler protocolResultHandler) {
		this.protocolResultHandler = protocolResultHandler;
	}
	
}
