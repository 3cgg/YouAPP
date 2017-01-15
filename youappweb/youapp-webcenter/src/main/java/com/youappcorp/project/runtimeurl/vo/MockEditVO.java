package com.youappcorp.project.runtimeurl.vo;

import me.bunny.app._c.data.web.model.JInputModel;
import me.bunny.app._c.data.web.model.JOutputModel;

public class MockEditVO implements JOutputModel ,JInputModel {
	
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
