package com.youappcorp.project.websupport.model;

import me.bunny.app._c._web.web.cache.resource.coderef.CodeRefCacheModel;
import me.bunny.app._c._web.web.cache.resource.coderef.CodeRefCacheModelUtil;

public class CodeTableCacheModel implements CodeRefCacheModel {
	
	private String type;
	
	private String code;
	
	private String name;
	
	public CodeTableCacheModel() {
	}
	
	public CodeTableCacheModel(String type, String code, String name) {
		this.type = type;
		this.code = code;
		this.name = name;
	}

	@Override
	public String getUri() {
		return CodeRefCacheModelUtil.key(this);
	}

	@Override
	public String type() {
		return type;
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public String name() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
