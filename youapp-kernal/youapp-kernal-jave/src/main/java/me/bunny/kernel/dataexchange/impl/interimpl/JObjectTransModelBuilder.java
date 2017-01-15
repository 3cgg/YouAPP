package me.bunny.kernel.dataexchange.impl.interimpl;

import me.bunny.kernel.jave.base64.JBase64;
import me.bunny.kernel.jave.base64.JBase64FactoryProvider;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.utils.JObjectSerializableUtils;

public class JObjectTransModelBuilder {

	private static JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	
	private JObjectTransModel objectTramsModel;
	
	public  static JObjectTransModelBuilder get(JObjectTransModelProtocol protocol){
		JObjectTransModelBuilder protocolSendBuilder=  new JObjectTransModelBuilder();
		protocolSendBuilder.objectTramsModel=new JObjectTransModel();
		protocolSendBuilder.objectTramsModel.setProtocol(protocol);
		return protocolSendBuilder;
	}
	
	private JObjectTransModelBuilder() {
	}
	
	public JObjectTransModelBuilder putData(String key,Object object){
		objectTramsModel.getParams().put(key, object);
		return this;
	}
	
	/**
	 * build {@link JObjectTransModel} , then return the base64 String of that
	 * @return
	 */
	public String buildObjectTransModel(){
		try{
			JObjectTransModelProtocol protocol=objectTramsModel.getProtocol();
			switch (protocol) {
			case JSON:
				return base64Service.encodeBase64String(JJSON.get().formatObject(objectTramsModel).getBytes("utf-8"));
			case OBJECT:
				return base64Service.encodeBase64String(JObjectSerializableUtils.serializeObject(objectTramsModel));
			default:
				throw new IllegalArgumentException("protocol: "+protocol);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
