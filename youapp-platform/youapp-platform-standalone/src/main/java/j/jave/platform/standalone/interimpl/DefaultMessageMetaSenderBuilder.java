package j.jave.platform.standalone.interimpl;

import j.jave.kernal.dataexchange.modelprotocol.JAbstractSenderBuilder;
import j.jave.kernal.dataexchange.modelprotocol.JDefaultProtocolSender;

public class DefaultMessageMetaSenderBuilder extends JAbstractSenderBuilder<DefaultMessageMetaSenderBuilder> {

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
	
	public DefaultMessageMetaSenderBuilder build(){
		defaultMessageMeta.setUrl(url);
		protocolSender=new JDefaultProtocolSender(defaultMessageMeta);
		protocolSender.setUrl(this.url)
			.setReceiveHandler(receiveHandler)
			.setSendHandler(sendHandler)
			.setServerHandler(serverHandler);
		return this;
	}
	
}
