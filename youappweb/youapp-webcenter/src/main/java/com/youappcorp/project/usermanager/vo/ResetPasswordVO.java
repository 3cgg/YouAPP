/**
 * 
 */
package com.youappcorp.project.usermanager.vo;

import me.bunny.app._c.data.web.model.JInputModel;

public class ResetPasswordVO implements JInputModel{
	private String userId;
	
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
