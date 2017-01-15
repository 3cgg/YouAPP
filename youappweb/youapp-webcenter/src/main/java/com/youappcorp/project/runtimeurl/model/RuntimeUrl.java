package com.youappcorp.project.runtimeurl.model;

import me.bunny.kernel.jave.model.JModel;
import me.bunny.kernel.jave.support._package.JDefaultMethodMeta;

public class RuntimeUrl implements JModel {

	private String containerUnique;
	
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

	public String getContainerUnique() {
		return containerUnique;
	}

	public void setContainerUnique(String containerUnique) {
		this.containerUnique = containerUnique;
	}
	
	
}
