/**
 * 
 */
package com.youappcorp.project.usermanager.vo;

import me.bunny.app._c.data.web.model.JInputModel;

public class UserRegisterInVO implements JInputModel{
	
	private String userName;
	
	private String password;
	
	private String retypePassword;
	
	private String natureName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	

	
	
	
	
}
