/**
 * 
 */
package com.youappcorp.project.usermanager.vo;

import me.bunny.app._c.data.web.model.JInputModel;

/**
 * @author J
 */
public class UserGroupInVO implements JInputModel {

	private String groupId;
	
	private String userId;


	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}
