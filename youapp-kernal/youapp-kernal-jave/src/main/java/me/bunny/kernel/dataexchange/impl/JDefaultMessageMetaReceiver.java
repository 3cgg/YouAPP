package me.bunny.kernel.dataexchange.impl;

import me.bunny.kernel._c.base64.JBase64;
import me.bunny.kernel._c.base64.JBase64FactoryProvider;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JObjectSerializableUtils;
import me.bunny.kernel.dataexchange.channel.JMessage;
import me.bunny.kernel.dataexchange.exception.JDataExchangeException;
import me.bunny.kernel.dataexchange.model.DefaultMessageMeta;
import me.bunny.kernel.dataexchange.model.MessageMeta;

class JDefaultMessageMetaReceiver {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	protected final byte[] bytes;
	
	/**
	 * 
	 * @param bytes the {@link JMessage} JSON string bytes
	 */
	public JDefaultMessageMetaReceiver(byte[] bytes) {
		this.bytes = bytes;
	}

	public MessageMeta receive() {
		try{
			JMessage message= JJSON.get().parse(new String(bytes,"utf-8"), JMessage.class);
			if(JEncoderRegisterService.JSON.equals(message.getDataByteEncoder())){
				byte[] bytes=base64Service.decodeBase64(message.getData());
				MessageMeta messageMeta=JJSON.get().parse(new String(bytes,"utf-8"), DefaultMessageMeta.class); 
				return messageMeta;
			}
			else if(JEncoderRegisterService.OBJECT_BYTES.equals(message.getDataByteEncoder())){
				byte[] bytes=base64Service.decodeBase64(message.getData());
				MessageMeta messageMeta=JObjectSerializableUtils.deserialize(bytes, DefaultMessageMeta.class);
				return messageMeta;
			}
			return null;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JDataExchangeException(e);
		}
	}
	
//	protected abstract byte[] doReceive() throws Exception;
//	
//	static class ObjectProtocolReceiver extends JProtocolReceiver{
//		
//		public ObjectProtocolReceiver(byte[] bytes) {
//			super(bytes);
//		}
//
//		@Override
//		protected byte[] doReceive() throws Exception{
//			ObjectTransModelMessage message= JJSON.get().parse(new String(bytes,"utf-8"), ObjectTransModelMessage.class);
//			return base64Service.decodeBase64(message.getData());
//		}
//		
//	} 
//	
//	static class JSONProtocolReceiver extends JProtocolReceiver{
//		
//		public JSONProtocolReceiver(byte[] bytes) {
//			super(bytes);
//		}
//
//		@Override
//		protected JObjectTransModel doReceive() throws JDataExchangeException{
//			ObjectTransModelMessage message=null;
//			try {
//				objectTransModel=JJSON.get().parse(new String(bytes,"utf-8"), JObjectTransModel.class);
//				
//				List<JObjectWrapper> objectWrappers= objectTransModel.getObjectWrappers();
//				if(JCollectionUtils.hasInCollect(objectWrappers)){
//					for(JObjectWrapper objectWrapper:objectWrappers){
//						String fullClassName=objectWrapper.getFullClassName();
//						Class<?> clazz=JClassUtils.load(fullClassName);
//						Object mayBeMap=objectWrapper.getObject();
//						if(!clazz.isInstance(mayBeMap)){
//							Object object=JClassUtils.newObject(clazz);
//							// data cast
//							if(Map.class.isInstance(mayBeMap)){
//								Map<String,Object> objMap=(Map<String, Object>) mayBeMap;
//								for(Entry<String, Object> entry:objMap.entrySet()){
//									JClassUtils.set(entry.getKey(), entry.getValue(), object);
//								}
//							}
//							objectWrapper.setObject(object);
//						}
//					}
//				}
//			} catch (UnsupportedEncodingException e) {
//				throw new JDataExchangeException(e);
//			}
//			return objectTransModel;
//		}
//		
//	}
	
}
