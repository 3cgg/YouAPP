package j.jave.platform.basicwebcomp.access.subhub;

import j.jave.kernal.jave.model.JModel;

public class AuthorizedResource implements JModel {

	/**
	 * unique user id
	 */
	private String userId;
	
	/**
	 * user name
	 */
	private String userName;
	
	/**
	 * request url.
	 */
	private String url;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
}
