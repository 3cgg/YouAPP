package com.youappcorp.project.runtimeurl.model;

import me.bunny.kernel.jave.model.JModel;



public class MockInfo implements JModel {
	
	private boolean mock;

	public boolean isMock() {
		return mock;
	}

	public void setMock(boolean mock) {
		this.mock = mock;
	}
	
}
