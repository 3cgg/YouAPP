/**
 * 
 */
package j.jave.platform.basicwebcomp.login.model;

import j.jave.platform.basicwebcomp.core.model.SimplePageCriteria;

/**
 * @author J
 */
public class RoleSearchCriteria extends SimplePageCriteria{
	
	private String roleCode;
	
	private String roleName;
	
	private String description;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
