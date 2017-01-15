package me.bunny.kernel.jave.cache;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.service.JCacheService;
import me.bunny.kernel.jave.service.JService;
import me.bunny.kernel.jave.support.JDefaultHashCacheService;
import me.bunny.kernel.jave.utils.JObjectSerializableUtils;

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
	public JInMemorySerializableCacheService doGetService() {
		return this;
	}
}
