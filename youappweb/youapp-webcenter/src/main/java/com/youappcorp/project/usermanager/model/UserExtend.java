/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.bunny.app._c.jpa.springjpa.JJpaBaseModel;

/**
 * @author J
 */
@Table(name="USERS_EXTEND")
@Entity
public class UserExtend extends JJpaBaseModel{
	
	private String userId;
	
	private String userName;
	
	private String userImage;
	
	private String natureName;
	
	@Column(name="USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="USER_IMAGE")
	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	@Column(name="NATURE_NAME")
	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	
	
	
}
