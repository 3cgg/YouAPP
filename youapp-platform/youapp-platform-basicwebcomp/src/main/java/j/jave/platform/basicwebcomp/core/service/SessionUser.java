package j.jave.platform.basicwebcomp.core.service;

import j.jave.kernal.jave.model.JModel;

public class SessionUser implements JModel {

	private String userName;
	
	private String userId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
