package j.jave.framework.components.login.model;

import j.jave.framework.model.JBaseModel;

import java.sql.Timestamp;

public class UserTracker extends JBaseModel {

	/**
	 * User ID
	 */
	private String userId;
	
	/**
	 * ip from the client 
	 */
	private String ip;
	
	/**
	 * user name 
	 */
	private String userName;
	
	/**
	 * login on the time . 
	 */
	private Timestamp loginTime;
	
	/**
	 * the client that login from
	 */
	private String loginClient;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginClient() {
		return loginClient;
	}

	public void setLoginClient(String loginClient) {
		this.loginClient = loginClient;
	}
	
}
