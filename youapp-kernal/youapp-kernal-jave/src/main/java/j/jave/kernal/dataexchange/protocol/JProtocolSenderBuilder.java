package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.dataexchange.protocol.JProtocolSender.JSONProtocolSender;
import j.jave.kernal.dataexchange.protocol.JProtocolSender.ObjectProtocolSender;
import j.jave.kernal.jave.utils.JAssert;

public class JProtocolSenderBuilder {

	private JObjectTransModel objectTramsModel;
	
	private JProtocolResultHandler protocolResultHandler;
	
	public  static JProtocolSenderBuilder get(JProtocol sendProtocol){
		JProtocolSenderBuilder protocolSendBuilder=  new JProtocolSenderBuilder();
		protocolSendBuilder.objectTramsModel=new JObjectTransModel();
		protocolSendBuilder.objectTramsModel.setSendProtocol(sendProtocol);
		return protocolSendBuilder;
	}
	
	private JProtocolSenderBuilder() {
	}
	
	public JProtocolSenderBuilder putData(Class<?> clazz,Object object){
		return putData(clazz.getName(), object);
	}
	
	public JProtocolSenderBuilder putData(String fullClassName,Object object){
		JObjectWrapper objectWrapper=new JObjectWrapper();
		JAssert.state(object.getClass().getName().equals(fullClassName), "please check the object matches the class / class-name.");
		objectWrapper.setFullClassName(fullClassName);
		objectWrapper.setObject(object);
		objectTramsModel.setObjectWrapper(objectWrapper);
		return this;
	}
	
	public JProtocolSenderBuilder setExptectedProtocol(JProtocol expectedProtocol){
		objectTramsModel.setExpectedProtocol(expectedProtocol);
		return this;
	}
	
	public JProtocolSenderBuilder setURL(String url){
		objectTramsModel.setUrl(url);
		return this;
	}
	
	public JProtocolSenderBuilder setProtocolResultHandler(
			JProtocolResultHandler protocolResultHandler) {
		this.protocolResultHandler = protocolResultHandler;
		return this;
	}
	
	public JProtocolSender build(){
		JProtocolSender protocolSender=null;
		if(objectTramsModel.getSendProtocol()==JProtocol.OBJECT){
			protocolSender=new ObjectProtocolSender(objectTramsModel);
		}
		else if(objectTramsModel.getSendProtocol()==JProtocol.JSON){
			protocolSender=new JSONProtocolSender(objectTramsModel);
		}
		protocolSender.setProtocolResultHandler(protocolResultHandler);
		return protocolSender;
	}
	
}
