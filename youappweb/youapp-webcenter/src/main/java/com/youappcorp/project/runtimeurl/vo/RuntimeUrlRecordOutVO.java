package com.youappcorp.project.runtimeurl.vo;

import j.jave.platform.data.web.model.JOutputModel;

public class RuntimeUrlRecordOutVO implements JOutputModel {

	private String containerUnique;
	
	private String id;

	private String url;
	
	private String name;
	
	private String desc;
	
	private MethodInfoVO methodInfo;
	
	private MockInfoVO mockInfo;
	
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

	
	public MethodInfoVO getMethodInfo() {
		return methodInfo;
	}

	public void setMethodInfo(MethodInfoVO methodInfo) {
		this.methodInfo = methodInfo;
	}

	public MockInfoVO getMockInfo() {
		return mockInfo;
	}

	public void setMockInfo(MockInfoVO mockInfo) {
		this.mockInfo = mockInfo;
	}

	public String getContainerUnique() {
		return containerUnique;
	}

	public void setContainerUnique(String containerUnique) {
		this.containerUnique = containerUnique;
	}
	
}
