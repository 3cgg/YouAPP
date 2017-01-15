package me.bunny.kernel.dataexchange.impl;

import me.bunny.kernel._c.base64.JBase64;
import me.bunny.kernel._c.base64.JBase64FactoryProvider;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.dataexchange.channel.JMessage;
import me.bunny.kernel.dataexchange.channel.JMessageChannel;
import me.bunny.kernel.dataexchange.channel.JResponseFuture;
import me.bunny.kernel.dataexchange.exception.JDataExchangeException;
import me.bunny.kernel.dataexchange.model.MessageMeta;

class JDefaultMessageMetaSender {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	private JByteDecoder receiveByteDecoder;
	
	protected final MessageMeta messageMeta;
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	public JDefaultMessageMetaSender(MessageMeta messageMeta) {
		this.messageMeta = messageMeta;
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
		message.setUrl(messageMeta.url());
		message.setDataByteEncoder(JEncoderRegisterService.JSON);
		message.setData(base64Service.encodeBase64String(
				JJSON.get().formatObject(messageMeta).getBytes("utf-8")));
		byte[] bytes=null;
		JResponseFuture responseFuture= new JMessageChannel().write(message);
		if(responseFuture.await()!=null){
			JMessage modelMessage=(JMessage) responseFuture.getResponse();
			String data64=modelMessage.getData();
			bytes= base64Service.decodeBase64(data64);
		}
		return bytes;
		
	}
	
	public JDefaultMessageMetaSender setReceiveByteDecoder(JByteDecoder receiveByteDecoder) {
		this.receiveByteDecoder = receiveByteDecoder;
		return this;
	}
	
}
