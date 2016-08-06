/**
 * 
 */
package com.youappcorp.project.codetable.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 *
 */
@JTable(name="PARAM_TYPE")
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
	@JColumn(name="CODE",type=JSQLType.VARCHAR,length=32)
	private String code;
	
	/**
	 * the nature name 
	 */
	@JColumn(name="NAME",type=JSQLType.VARCHAR,length=128)
	private String name;
	
	/**
	 * optional description 
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=512)
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
