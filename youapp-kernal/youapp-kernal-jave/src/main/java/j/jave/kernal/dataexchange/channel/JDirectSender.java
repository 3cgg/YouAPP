package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;

public abstract class JDirectSender {
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	public final JMessage send(JMessage message) throws Exception {
		byte[] bytes=doSend(message);
		JMessage response=new JMessage();
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

	protected abstract byte[] doSend(JMessage message) throws Exception;
	
	/**
	 * extract the real data
	 * @param objectTransModel
	 * @return the byte arrays
	 */
	protected final byte[] getSenderData(JMessage message) throws Exception{
		byte[] data=null;
		String objectJSON=JJSON.get().formatObject(message);
		data=objectJSON.getBytes("utf-8");
		return data;
	}
	
	
	

	
	
}
