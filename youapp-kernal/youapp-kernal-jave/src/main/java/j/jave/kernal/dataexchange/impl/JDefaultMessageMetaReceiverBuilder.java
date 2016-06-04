package j.jave.kernal.dataexchange.impl;

import j.jave.kernal.dataexchange.model.MessageMeta;


public class JDefaultMessageMetaReceiverBuilder {
	
	private byte[] bytes;
	
	private JDefaultMessageMetaReceiver defaultMessageMetaReceiver;
	
	public  static JDefaultMessageMetaReceiverBuilder get(byte[] bytes){
		JDefaultMessageMetaReceiverBuilder protocolSendBuilder=  new JDefaultMessageMetaReceiverBuilder();
		protocolSendBuilder.bytes=bytes;
		return protocolSendBuilder;
	}
	
	private JDefaultMessageMetaReceiverBuilder() {
	}
	
	public MessageMeta receive(){
		return  defaultMessageMetaReceiver.receive();
	}
	
	public JDefaultMessageMetaReceiverBuilder build(){
		defaultMessageMetaReceiver=new JDefaultMessageMetaReceiver(bytes);
		return this;
	}
	
}
