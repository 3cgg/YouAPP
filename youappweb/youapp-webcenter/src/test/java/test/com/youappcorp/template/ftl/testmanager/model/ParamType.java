/**
 * 
 */
package test.com.youappcorp.template.ftl.testmanager.model;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 *
 */
@Entity
@Table(name="PARAM_TYPE")
public class ParamType extends JJpaBaseModel {
	
	public ParamType() {
	}
	
	
	public ParamType(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}



	public ParamType(String code, String name, String description) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
	}



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

	@Column(name="CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
