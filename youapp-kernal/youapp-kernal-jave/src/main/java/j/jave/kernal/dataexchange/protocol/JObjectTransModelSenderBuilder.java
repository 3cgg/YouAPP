package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.dataexchange.protocol.JProtocolSender.JSONProtocolSender;
import j.jave.kernal.dataexchange.protocol.JProtocolSender.ObjectProtocolSender;
import j.jave.kernal.jave.utils.JAssert;

public class JObjectTransModelSenderBuilder {

	private JObjectTransModel objectTramsModel;
	
	private JProtocolByteHandler protocolByteHandler;
	
	public  static JObjectTransModelSenderBuilder get(JProtocol sendProtocol){
		JObjectTransModelSenderBuilder protocolSendBuilder=  new JObjectTransModelSenderBuilder();
		protocolSendBuilder.objectTramsModel=new JObjectTransModel();
		protocolSendBuilder.objectTramsModel.setProtocol(sendProtocol);
		return protocolSendBuilder;
	}
	
	private JObjectTransModelSenderBuilder() {
	}
	
	public JObjectTransModelSenderBuilder putData(Class<?> clazz,Object object){
		return putData(clazz.getName(), object);
	}
	
	public JObjectTransModelSenderBuilder putData(String fullClassName,Object object){
		JObjectWrapper objectWrapper=new JObjectWrapper();
		JAssert.state(object.getClass().getName().equals(fullClassName), "please check the object matches the class / class-name.");
		objectWrapper.setFullClassName(fullClassName);
		objectWrapper.setObject(object);
		objectTramsModel.setObjectWrapper(objectWrapper);
		return this;
	}
	
	public JObjectTransModelSenderBuilder setURL(String url){
		objectTramsModel.setUrl(url);
		return this;
	}
	
	public JObjectTransModelSenderBuilder setProtocolByteHandler(JProtocolByteHandler protocolByteHandler) {
		this.protocolByteHandler = protocolByteHandler;
		return this;
	}
	
	public JProtocolSender build(){
		JProtocolSender protocolSender=null;
		if(objectTramsModel.getProtocol()==JProtocol.OBJECT){
			protocolSender=new ObjectProtocolSender(objectTramsModel);
		}
		else if(objectTramsModel.getProtocol()==JProtocol.JSON){
			protocolSender=new JSONProtocolSender(objectTramsModel);
		}
		protocolSender.setUrl(objectTramsModel.getUrl());
		protocolSender.setProtocolByteHandler(protocolByteHandler);
		protocolSender.setProtocol(objectTramsModel.getProtocol());
		return protocolSender;
	}
	
}
