/**
 * 
 */
package j.jave.platform.basicwebcomp.login.model;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;

/**
 * @author J
 */
@JTable(name="GROUPS")
public class Group extends JBaseModel {

	@JColumn(name="GROUPCODE",type=JSQLType.VARCHAR,length=32)
	private String groupCode;
	
	@JColumn(name="GROUPNAME",type=JSQLType.VARCHAR,length=64)
	private String groupName;
	
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=256)
	private String description;

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
