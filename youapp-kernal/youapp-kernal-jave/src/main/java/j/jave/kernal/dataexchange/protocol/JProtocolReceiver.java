package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.dataexchange.exception.JDataExchangeException;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class JProtocolReceiver {

	private final JLogger LOGGER=JLoggerFactory.getLogger(this.getClass());
	
	protected final byte[] bytes;
	
	public JProtocolReceiver(byte[] bytes) {
		super();
		this.bytes = bytes;
	}

	public JObjectTransModel receive() {
		try{
			JObjectTransModel objectTransModel=doReceive();
			return objectTransModel;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JDataExchangeException(e);
		}
	}

	protected abstract JObjectTransModel doReceive() throws JDataExchangeException;
	
	static class ObjectProtocolReceiver extends JProtocolReceiver{
		
		public ObjectProtocolReceiver(byte[] bytes) {
			super(bytes);
		}

		@Override
		protected JObjectTransModel doReceive() throws JDataExchangeException{
			JObjectTransModel objectTransModel=JObjectSerializableUtils.deserialize(bytes, JObjectTransModel.class);
			return objectTransModel;
		}
		
	} 
	
	static class JSONProtocolReceiver extends JProtocolReceiver{
		
		public JSONProtocolReceiver(byte[] bytes) {
			super(bytes);
		}

		@Override
		protected JObjectTransModel doReceive() throws JDataExchangeException{
			JObjectTransModel objectTransModel=null;
			try {
				objectTransModel=JJSON.get().parse(new String(bytes,"utf-8"), JObjectTransModel.class);
				
				List<JObjectWrapper> objectWrappers= objectTransModel.getObjectWrappers();
				if(JCollectionUtils.hasInCollect(objectWrappers)){
					for(JObjectWrapper objectWrapper:objectWrappers){
						String fullClassName=objectWrapper.getFullClassName();
						Class<?> clazz=JClassUtils.load(fullClassName);
						Object mayBeMap=objectWrapper.getObject();
						if(!clazz.isInstance(mayBeMap)){
							Object object=JClassUtils.newObject(clazz);
							// data cast
							if(Map.class.isInstance(mayBeMap)){
								Map<String,Object> objMap=(Map<String, Object>) mayBeMap;
								for(Entry<String, Object> entry:objMap.entrySet()){
									JClassUtils.set(entry.getKey(), entry.getValue(), object);
								}
							}
							objectWrapper.setObject(object);
						}
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new JDataExchangeException(e);
			}
			return objectTransModel;
		}
		
	}
	
}
