/**
 * 
 */
package j.jave.framework.components.runtimeload.model;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.model.support.JColumn;
import j.jave.framework.commons.model.support.JSQLType;
import j.jave.framework.commons.model.support.JTable;

/**
 * @author J
 */
@JTable(name="RuntimeLoad")
public class RuntimeLoad extends JBaseModel {
	
	/**
	 *用户CODE 
	 */
	@JColumn(name="NAME",type=JSQLType.VARCHAR,length=32)
	private String name;

	/**
	 * optional description 
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=512)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
