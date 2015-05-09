package j.jave.framework.components.resource.model;

import j.jave.framework.components.login.model.Role;
import j.jave.framework.model.JBaseModel;
import j.jave.framework.model.support.JColumn;
import j.jave.framework.model.support.JSQLType;
import j.jave.framework.model.support.JTable;

@JTable(name="RESOURCES_ROLES")
public class ResourceRole extends JBaseModel {
	
	/**
	 * ID reference to RESOURCE table. 
	 */
	@JColumn(name="RESOURCEID",type=JSQLType.VARCHAR,length=32)
	private String resourceId;

	/**
	 * ROLE ID 
	 */
	@JColumn(name="ROLEID",type=JSQLType.VARCHAR,length=32)
	private String roleId;
	
	/**
	 * Y OR N
	 */
	@JColumn(name="ENABLE",type=JSQLType.VARCHAR,length=1)
	private String enable;
	
	/**
	 * description. 
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;
	
	private Role role;
	
	private Resource resource;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	
	
	
}
