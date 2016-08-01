package com.youappcorp.project.runtimeurl.model;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;

public class RuntimeUrl implements JModel {

	private String id;

	private String url;
	
	private String name;
	
	private String desc;
	
	private JDefaultMethodMeta methodMeta;
	
	private MockInfo mockInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public JDefaultMethodMeta getMethodMeta() {
		return methodMeta;
	}

	public void setMethodMeta(JDefaultMethodMeta methodMeta) {
		this.methodMeta = methodMeta;
	}

	public MockInfo getMockInfo() {
		return mockInfo;
	}

	public void setMockInfo(MockInfo mockInfo) {
		this.mockInfo = mockInfo;
	}
	
}
