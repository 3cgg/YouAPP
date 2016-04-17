package com.youappcorp.project.usermanager.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author J
 *
 */
@JTable(name="USER_TRACKER")
@Table(name="USER_TRACKER")
@Entity
public class UserTracker extends JJpaBaseModel {

	/**
	 * User ID
	 */
	@JColumn(name="USERID",type=JSQLType.VARCHAR,length=32)
	private String userId;
	
	/**
	 * ip from the client 
	 */
	@JColumn(name="IP",type=JSQLType.VARCHAR,length=32)
	private String ip;
	
	/**
	 * user name 
	 */
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	private String userName;
	
	/**
	 * login on the time . 
	 */
	@JColumn(name="LOGIN_TIME",type=JSQLType.TIMESTAMP)
	private Timestamp loginTime;
	
	/**
	 * the client that login from
	 */
	@JColumn(name="LOGIN_CLIENT",type=JSQLType.VARCHAR,length=256)
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
