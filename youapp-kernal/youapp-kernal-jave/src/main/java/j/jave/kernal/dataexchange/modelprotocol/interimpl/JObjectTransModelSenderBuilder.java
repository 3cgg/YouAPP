package j.jave.kernal.dataexchange.modelprotocol.interimpl;

import j.jave.kernal.dataexchange.modelprotocol.JAbstractSenderBuilder;
import j.jave.kernal.dataexchange.modelprotocol.JDefaultProtocolSender;
import j.jave.kernal.jave.utils.JAssert;

public class JObjectTransModelSenderBuilder extends JAbstractSenderBuilder<JObjectTransModelSenderBuilder> {

	private JObjectTransModel objectTramsModel;
	
	public  static JObjectTransModelSenderBuilder get(){
		JObjectTransModelSenderBuilder protocolSendBuilder=  new JObjectTransModelSenderBuilder();
		protocolSendBuilder.objectTramsModel=new JObjectTransModel();
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
	
	public JObjectTransModelSenderBuilder build(){
		protocolSender=new JDefaultProtocolSender(objectTramsModel);
		protocolSender.setUrl(this.url)
			.setReceiveHandler(receiveHandler)
			.setSendHandler(sendHandler)
			.setServerHandler(serverHandler);
		return this;
	}
	
}
