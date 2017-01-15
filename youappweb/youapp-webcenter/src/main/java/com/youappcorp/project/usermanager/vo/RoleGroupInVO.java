/**
 * 
 */
package com.youappcorp.project.usermanager.vo;

import me.bunny.app._c.data.web.model.JInputModel;

/**
 * @author J
 */
public class RoleGroupInVO implements JInputModel {

	private String roleId;
	
	private String groupId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
