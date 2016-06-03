package j.jave.kernal.dataexchange.modelprotocol.interimpl;

import j.jave.kernal.dataexchange.modelprotocol.JBaseSenderBuilder;
import j.jave.kernal.dataexchange.modelprotocol.JDefaultMessageSender;
import j.jave.kernal.jave.utils.JAssert;

public class JObjectTransModelSenderBuilder extends JBaseSenderBuilder<JObjectTransModelSenderBuilder> {

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
		protocolSender=new JDefaultMessageSender(objectTramsModel);
		protocolSender.setUrl(this.url)
			.setReceiveByteDecoder(receiveByteDecoder)
			.setSendObjectEncoder(sendObjectEncoder)
			.setDataByteEncoder(dataByteEncoder);
		return this;
	}
	
}
