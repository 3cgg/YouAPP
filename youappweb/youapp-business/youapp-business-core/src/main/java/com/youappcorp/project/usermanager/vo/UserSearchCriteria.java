/**
 * 
 */
package com.youappcorp.project.usermanager.vo;

import j.jave.platform.data.web.model.BaseCriteria;

/**
 * @author J
 */
public class UserSearchCriteria extends BaseCriteria{
	
	private String userName;
	
	private String status;
	
	private String registerTimeStart;
	
	private String registerTimeEnd;
	
	private String natureName;
	

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

	public String getRegisterTimeStart() {
		return registerTimeStart;
	}

	public void setRegisterTimeStart(String registerTimeStart) {
		this.registerTimeStart = registerTimeStart;
	}

	public String getRegisterTimeEnd() {
		return registerTimeEnd;
	}

	public void setRegisterTimeEnd(String registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}

	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}

}
