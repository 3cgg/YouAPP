/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 *
 */
@Table(name="USERS")
@Entity
public class User extends JJpaBaseModel{
	
	private String userName;
	
	private String password;
	
	private String status;
	
	private Timestamp registerTime;
	
	
	@Column(name="REGISTER_TIME")
	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="USERNAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
