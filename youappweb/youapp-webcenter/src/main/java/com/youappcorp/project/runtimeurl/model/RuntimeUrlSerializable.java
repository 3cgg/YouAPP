package com.youappcorp.project.runtimeurl.model;

import me.bunny.kernel.jave.model.JModel;

public class RuntimeUrlSerializable implements JModel {
	
	private String url;
	
	private boolean mock;

	public boolean isMock() {
		return mock;
	}

	public void setMock(boolean mock) {
		this.mock = mock;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
