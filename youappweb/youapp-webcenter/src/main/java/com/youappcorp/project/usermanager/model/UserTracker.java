package com.youappcorp.project.usermanager.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.bunny.app._c.jpa.springjpa.JJpaBaseModel;

/**
 * 
 * @author J
 *
 */
@Table(name="USER_TRACKER")
@Entity
public class UserTracker extends JJpaBaseModel {

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

	@Column(name="USERID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="IP")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name="USERNAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="LOGIN_TIME")
	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name="LOGIN_CLIENT")
	public String getLoginClient() {
		return loginClient;
	}

	public void setLoginClient(String loginClient) {
		this.loginClient = loginClient;
	}
	
}
