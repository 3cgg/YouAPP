/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

/**
 * @author J
 */
@JTable(name="USERS_EXTEND")	
@Table(name="USERS_EXTEND")
@Entity
public class UserExtend extends JJpaBaseModel{
	
	@JColumn(name="USERID",type=JSQLType.VARCHAR,length=32)
	@Column(name="USERID")
	private String userId;
	
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	@Column(name="USERNAME")
	private String userName;
	
	@JColumn(name="USERIMAGE",type=JSQLType.VARCHAR,length=256)
	@Column(name="USERIMAGE")
	private String userImage;
	
	@JColumn(name="NATURENAME",type=JSQLType.VARCHAR,length=64)
	@Column(name="NATURENAME")
	private String natureName;
	
	@Transient
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	
	
	
}
