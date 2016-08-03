package com.youappcorp.project.runtimeurl.service;

import java.util.Collection;

import com.youappcorp.project.runtimeurl.model.RuntimeUrlSerializable;

public interface RuntimeUrlSerializeService {

	public void serialize(RuntimeUrlSerializable runtimeUrl) throws Exception;
	
	public void serialize(Collection<RuntimeUrlSerializable> runtimeUrls) throws Exception;
	
	public Collection<RuntimeUrlSerializable> read() throws Exception;
	
	public RuntimeUrlSerializable read(String url) throws Exception;
	
}
