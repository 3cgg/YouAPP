package j.jave.platform.standalone.interimpl;

import j.jave.kernal.dataexchange.modelprotocol.JBaseSenderBuilder;
import j.jave.kernal.dataexchange.modelprotocol.JDefaultMessageSender;

public class DefaultMessageMetaSenderBuilder extends JBaseSenderBuilder<DefaultMessageMetaSenderBuilder> {

	private DefaultMessageMeta defaultMessageMeta;
	
	public  static DefaultMessageMetaSenderBuilder get(){
		DefaultMessageMetaSenderBuilder protocolSendBuilder=  new DefaultMessageMetaSenderBuilder();
		protocolSendBuilder.defaultMessageMeta=new DefaultMessageMeta();
		return protocolSendBuilder;
	}
	
	private DefaultMessageMetaSenderBuilder() {
	}
	
	/**
	 * put the base64 form of any object
	 * @param data the base64 string
	 * @return
	 */
	public DefaultMessageMetaSenderBuilder putData(String data){
		defaultMessageMeta.setData(data);
		return this;
	}
	
	public DefaultMessageMetaSenderBuilder putDataEncoderPropertyForDefaultMessageMeta(String dataEncoder){
		defaultMessageMeta.setDataEncoder(dataEncoder);
		return this;
	}
	
	public DefaultMessageMetaSenderBuilder build(){
		defaultMessageMeta.setUrl(url);
		protocolSender=new JDefaultMessageSender(defaultMessageMeta);
		protocolSender.setUrl(this.url)
			.setReceiveByteDecoder(receiveByteDecoder)
			.setSendObjectEncoder(sendObjectEncoder)
			.setDataByteEncoder(dataByteEncoder);
		return this;
	}
	
}
