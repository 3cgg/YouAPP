package com.youappcorp.project.runtimeurl.service;

import j.jave.kernal.jave.service.JService;

import java.util.Collection;

import com.youappcorp.project.runtimeurl.model.RuntimeUrl;

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
	
}
