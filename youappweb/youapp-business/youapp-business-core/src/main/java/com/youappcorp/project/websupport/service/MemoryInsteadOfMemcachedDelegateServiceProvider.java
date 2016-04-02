package com.youappcorp.project.websupport.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisAddEvent;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisDeleteEvent;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisGetEvent;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisSetEvent;
import j.jave.platform.basicsupportcomp.support.memcached.subhub.MemcachedDelegateServiceProvider;

public class MemoryInsteadOfMemcachedDelegateServiceProvider
	implements MemcachedDelegateServiceProvider{

	
	private JDefaultHashCacheService hashCacheService=
			JServiceHubDelegate.get().getService(this, JDefaultHashCacheService.class);

	@Override
	public Object set(String key, int expiry, Object value) {
		return hashCacheService.putNeverExpired(key, value);
	}

	@Override
	public Object get(String key) {
		return hashCacheService.get(key);
	}

	@Override
	public void add(String key, int expiry, Object value) {
		hashCacheService.putNeverExpired(key, value);
	}

	@Override
	public void delete(String key) {
		hashCacheService.remove(key);
	}

	@Override
	public Object putNeverExpired(String key, Object object) {
		return hashCacheService.putNeverExpired(key, object);
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
		return hashCacheService.putNeverExpired(event.getKey(), event.getValue());
	}

	@Override
	public Object trigger(JMemcachedDisDeleteEvent event) {
		return hashCacheService.remove(event.getKey());
	}

	@Override
	public Object trigger(JMemcachedDisSetEvent event) {
		return hashCacheService.putNeverExpired(event.getKey(), event.getValue());
	}

	@Override
	public Object trigger(JMemcachedDisGetEvent event) {
		return hashCacheService.get(event.getKey());
	}
	
}
