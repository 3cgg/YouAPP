package com.youappcorp.project.runtimeurl.service;

import java.util.Collection;

import com.youappcorp.project.runtimeurl.model.RuntimeUrl;

import me.bunny.kernel._c.service.JService;

public interface RuntimeUrlManagerService extends JService {
	
	void updateMockState(String url,boolean mock);
	
	RuntimeUrl getRuntimeUrlByUrl(String url);
	
	RuntimeUrl getRuntimeUrlById(String url);
	
	boolean exists(String url);
	
	boolean isMock(String url);
	
	/**
	 * 
	 * @return the collection should be sequence.
	 */
	Collection<RuntimeUrl> getAllRuntimeUrls();
	
	public void cleanup();
}
