package j.jave.kernal.dataexchange.modelprotocol;

import j.jave.kernal.dataexchange.channel.JMessage;
import j.jave.kernal.dataexchange.channel.JMessageChannel;
import j.jave.kernal.dataexchange.channel.JResponseFuture;
import j.jave.kernal.dataexchange.exception.JDataExchangeException;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

public class JDefaultMessageSender {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	private JByteDecoder receiveByteDecoder;
	
	private JObjectEncoder sendObjectEncoder;
	
	private String dataByteEncoder;
	
	protected final Object data;
	
	protected String url;
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	public JDefaultMessageSender(Object data) {
		super();
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public <T> T send() {
		try{
			byte[] bytes=doSend();
			if(receiveByteDecoder!=null){
				return (T) receiveByteDecoder.decode(bytes);
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

		JMessage message=new JMessage();
		message.setUrl(this.url);
		message.setDataByteEncoder(dataByteEncoder);
		message.setData(base64Service.encodeBase64String(sendObjectEncoder.encode(data)));
		byte[] bytes=null;
		JResponseFuture responseFuture= new JMessageChannel().write(message);
		if(responseFuture.await()!=null){
			JMessage modelMessage=(JMessage) responseFuture.getResponse();
			String data64=modelMessage.getData();
			bytes= base64Service.decodeBase64(data64);
		}
		return bytes;
		
	}
	
	public JDefaultMessageSender setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public JDefaultMessageSender setReceiveByteDecoder(JByteDecoder receiveByteDecoder) {
		this.receiveByteDecoder = receiveByteDecoder;
		return this;
	}

	public JDefaultMessageSender setSendObjectEncoder(JObjectEncoder sendObjectEncoder) {
		this.sendObjectEncoder = sendObjectEncoder;
		return this;
	}
	
	public JDefaultMessageSender setDataByteEncoder(String dataByteEncoder) {
		this.dataByteEncoder = dataByteEncoder;
		return this;
	}
	
}
