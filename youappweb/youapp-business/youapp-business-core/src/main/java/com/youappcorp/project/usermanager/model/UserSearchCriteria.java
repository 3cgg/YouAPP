/**
 * 
 */
package com.youappcorp.project.usermanager.model;

import j.jave.platform.data.web.model.BaseCriteria;

/**
 * @author J
 */
public class UserSearchCriteria extends BaseCriteria{
	
	private String userName;
	
	private String password;
	
	private String retypePassword;

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

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

}
