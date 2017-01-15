/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import java.sql.Timestamp;
import java.util.List;

import me.bunny.kernel._c.model.JModel;

/**
 * @author J
 */
public class UserDetail implements JModel{
	
	private String userId;
	
	private String userName;
	
	private String status;
	
	private Timestamp registerTime;
	
	private String userImage;
	
	private String natureName;
	
	private List<RoleRecord> roles;
	
	private List<GroupRecord> groups;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
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

	public List<RoleRecord> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleRecord> roles) {
		this.roles = roles;
	}

	public List<GroupRecord> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupRecord> groups) {
		this.groups = groups;
	}
}
