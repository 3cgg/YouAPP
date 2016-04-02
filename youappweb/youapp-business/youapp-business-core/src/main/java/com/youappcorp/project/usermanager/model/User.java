/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Administrator
 *
 */
@JTable(name="USERS")
@Table(name="USERS")
@Entity
public class User extends JJpaBaseModel{
	
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	@Column(name="USERNAME")
	private String userName;
	
	@JColumn(name="PASSWORD",type=JSQLType.VARCHAR,length=64)
	@Column(name="PASSWORD")
	private String password;
	
	@Transient
	private String retypePassword;
	
	@Transient
	private UserExtend userExtend;

	public UserExtend getUserExtend() {
		return userExtend;
	}

	public void setUserExtend(UserExtend userExtend) {
		this.userExtend = userExtend;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
