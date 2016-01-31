package j.jave.platform.basicwebcomp.resource.model;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.login.model.Group;

@JTable(name="RESOURCES_GROUPS")
public class ResourceGroup extends JBaseModel {
	
	/**
	 * ID reference to RESOURCE table. 
	 */
	@JColumn(name="RESOURCEID",type=JSQLType.VARCHAR,length=32)
	private String resourceId;

	/**
	 * GRPUP ID 
	 */
	@JColumn(name="GROUPID",type=JSQLType.VARCHAR,length=32)
	private String groupId;
	
	/**
	 * description. 
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;

	/**
	 * Y OR N
	 */
	@JColumn(name="ENABLE",type=JSQLType.VARCHAR,length=1)
	private String enable;
	
	
	private Resource resource;
	
	private Group group;
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	
	
	
}
