package me.bunny.modular._p.streaming.netty.client;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel._c.utils.JAssert;

public class RequestMeta implements JModel {

	private String url;
	
	private byte[] content;
	
	private Map<String, Object> headers=Maps.newHashMap();
	
	public void addHeader(String name,Object value){
		JAssert.isNotEmpty(name);
		headers.put(name, value);
	}
	
	public Object getHeader(String name){
		return headers.get(name);
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Map<String, Object> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}
	
}
