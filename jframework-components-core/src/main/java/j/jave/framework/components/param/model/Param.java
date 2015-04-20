/**
 * 
 */
package j.jave.framework.components.param.model;

import j.jave.framework.model.JBaseModel;

/**
 * @author Administrator
 *
 */
public class Param extends JBaseModel {
	
	/**
	 * the id the type of funciton. 
	 */
	private String functionId;
	
	/**
	 * the code 
	 */
	private String code;
	
	/**
	 * the nature name 
	 */
	private String name;
	
	/**
	 * optional description 
	 */
	private String description;

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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
