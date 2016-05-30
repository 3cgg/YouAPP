package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.dataexchange.protocol.JProtocol;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

public abstract class Sender {
	/**
	 * 
	 */
	public final ObjectTransModelMessage send(ObjectTransModelMessage message) throws Exception {
		byte[] bytes=doSend(message);
		ObjectTransModelMessage response=null;
		
		JProtocol protocol=message.getProtocol();
		switch (protocol) {
		case JSON:
			response=JJSON.get().parse(new String(bytes,"utf-8"),ObjectTransModelMessage.class);
			break;
		case OBJECT:
			response=JObjectSerializableUtils.deserialize(bytes, ObjectTransModelMessage.class);
			break;
		default:
			break;
		}
		return response;
	}

	protected abstract byte[] doSend(ObjectTransModelMessage message) throws Exception;
	
	/**
	 * extract the real data
	 * @param objectTransModel
	 * @return the byte arrays
	 */
	protected byte[] getSenderData(ObjectTransModelMessage message) throws Exception{
//		JProtocol protocol= message.getProtocol();
//		JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
		byte[] data=null;
		String objectJSON=JJSON.get().formatObject(message);
		data=objectJSON.getBytes("utf-8");
//		switch (protocol) {
//		case JSON:
//			String objectJSON=JJSON.get().formatObject(message);
//			data=objectJSON.getBytes("utf-8");
//		case OBJECT:
//			byte[] bytes=JObjectSerializableUtils.serializeObject(message);
//			data=bytes;
//		default:
//			break;
//		}
		return data;
	}
	
	
	

	
	
}
