package com.youappcorp.project.websupport.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisAddEvent;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisDeleteEvent;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisGetEvent;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisSetEvent;
import j.jave.platform.basicsupportcomp.support.memcached.subhub.MemcachedDelegateServiceProvider;

public class MemoryInsteadOfMemcachedDelegateServiceProvider
	implements MemcachedDelegateServiceProvider{

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
	public Object set(String key, int expiry, Object value) {
		return hashCacheService.putNeverExpired(key, getSerialObject(value));
	}

	@Override
	public Object get(String key) {
		return getInnerObject((SerialObject)hashCacheService.get(key));
	}

	@Override
	public void add(String key, int expiry, Object value) {
		hashCacheService.putNeverExpired(key, getSerialObject(value));
	}

	@Override
	public void delete(String key) {
		hashCacheService.remove(key);
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
	public Object trigger(JMemcachedDisAddEvent event) {
		return putNeverExpired(event.getKey(), event.getValue());
	}

	@Override
	public Object trigger(JMemcachedDisDeleteEvent event) {
		return remove(event.getKey());
	}

	@Override
	public Object trigger(JMemcachedDisSetEvent event) {
		return putNeverExpired(event.getKey(), event.getValue());
	}

	@Override
	public Object trigger(JMemcachedDisGetEvent event) {
		return get(event.getKey());
	}

	@Override
	public void put(String key, int expiry, Object value) {
		put(key, expiry, value);
	}
	
}
