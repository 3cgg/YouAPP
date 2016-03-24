/**
 * 
 */
package j.jave.platform.basicwebcomp.param.model;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.IdClass;
import javax.persistence.SqlResultSetMapping;


/**
 * @author J
 *
 */
public class ParamQuery {
	
	public ParamQuery() {
		// TODO Auto-generated constructor stub
	}
	
	public ParamQuery(String name,String code) {
		this.name=name;
		this.code=code;
	}
	
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
