package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;

public abstract class Sender {
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	public final Message send(Message message) throws Exception {
		byte[] bytes=doSend(message);
		Message response=new Message();
		response.setUrl(message.getUrl());
		response.setData(base64Service.encodeBase64String(bytes));
//		JProtocol protocol=message.getProtocol();
//		switch (protocol) {
//		case JSON:
//			response=JJSON.get().parse(new String(bytes,"utf-8"),ObjectTransModelMessage.class);
//			break;
//		case OBJECT:
//			response=JObjectSerializableUtils.deserialize(bytes, ObjectTransModelMessage.class);
//			break;
//		default:
//			break;
//		}
		return response;
	}

	protected abstract byte[] doSend(Message message) throws Exception;
	
	/**
	 * extract the real data
	 * @param objectTransModel
	 * @return the byte arrays
	 */
	protected byte[] getSenderData(Message message) throws Exception{
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
