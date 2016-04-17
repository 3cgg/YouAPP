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
 * @author J
 */
@JTable(name="USERS_EXTEND")	
@Table(name="USERS_EXTEND")
@Entity
public class UserExtend extends JJpaBaseModel{
	
	@JColumn(name="USERID",type=JSQLType.VARCHAR,length=32)
	private String userId;
	
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	private String userName;
	
	@JColumn(name="USERIMAGE",type=JSQLType.VARCHAR,length=256)
	private String userImage;
	
	@JColumn(name="NATURENAME",type=JSQLType.VARCHAR,length=64)
	private String natureName;
	
	private User user;

	@Transient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name="USERID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="USERNAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="USERIMAGE")
	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	@Column(name="NATURENAME")
	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	
	
	
}
