/**
 * 
 */
package j.jave.framework.components.login.model;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.model.support.JColumn;
import j.jave.framework.commons.model.support.JSQLType;
import j.jave.framework.commons.model.support.JTable;

/**
 * @author J
 */
@JTable(name="ROLES_GROUPS")
public class RoleGroup extends JBaseModel {

	@JColumn(name="ROLEID",type=JSQLType.VARCHAR,length=32)
	private String roleId;
	
	@JColumn(name="GROUPID",type=JSQLType.VARCHAR,length=32)
	private String groupId;
	
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;
	
	private Role role;
	
	private Group group;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
