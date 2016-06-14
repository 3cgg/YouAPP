package j.jave.kernal.jave.cache;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.service.JCacheService;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

public class JInMemorySerializableCacheService
extends JServiceFactorySupport<JInMemorySerializableCacheService>
implements JService , JCacheService{

	public static class SerialObject{
		private String className;
		private byte[] bytes;
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public byte[] getBytes() {
			return bytes;
		}
		public void setBytes(byte[] bytes) {
			this.bytes = bytes;
		}
	}
	
	
	private JDefaultHashCacheService hashCacheService=
			JServiceHubDelegate.get().getService(this, JDefaultHashCacheService.class);

	
	@Override
	public Object get(String key) {
		return getInnerObject((SerialObject)hashCacheService.get(key));
	}

	@Override
	public Object putNeverExpired(String key, Object object) {
		return hashCacheService.putNeverExpired(key, getSerialObject(object));
	}
	
	private SerialObject getSerialObject(Object object){
		SerialObject serialObject=new SerialObject();
		serialObject.setClassName(object.getClass().getName());
		serialObject.setBytes(JObjectSerializableUtils.serializeObject(object));
		return serialObject;
	}
	
	private Object getInnerObject(SerialObject serialObject){
		if(serialObject==null){
			return null;
		}
		return JObjectSerializableUtils.deserialize(serialObject.getBytes(), JClassUtils.load(serialObject.getClassName()));
	}

	@Override
	public Object remove(String key) {
		return hashCacheService.remove(key);
	}

	@Override
	public boolean contains(String key) {
		return hashCacheService.contains(key);
	}

	@Override
	public Object put(String key, int expiry, Object value) {
		return hashCacheService.put(key, expiry, getSerialObject(value));
	}
	
	@Override
	public JInMemorySerializableCacheService getService() {
		return this;
	}
}
