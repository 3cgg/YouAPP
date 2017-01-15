/**
 * 
 */
package com.youappcorp.project.codetable.model;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;
import me.bunny.kernel.jave.model.support.JColumn;
import me.bunny.kernel.jave.model.support.JSQLType;
import me.bunny.kernel.jave.model.support.JTable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import com.youappcorp.project.websupport.model.CodeTableCacheModel;

/**
 * @author J
 */
@SqlResultSetMappings(
		{
			@SqlResultSetMapping(name = "ParamQueryMapping",
					classes={@ConstructorResult(
							targetClass=CodeTableCacheModel.class,
							columns={
								@ColumnResult(name="TYPE",type=String.class),
								@ColumnResult(name="CODE",type=String.class),
								@ColumnResult(name="NAME",type=String.class)
								
							}
							)}
			),
			@SqlResultSetMapping(name = "CodeTableQueryMapping",
			classes={@ConstructorResult(
					targetClass=CodeTableCacheModel.class,
					columns={
						@ColumnResult(name="TYPE",type=String.class),
						@ColumnResult(name="CODE",type=String.class),
						@ColumnResult(name="NAME",type=String.class)
					}
					)}
			)
		}
		)
@Entity
@Table(name="PARAM_CODE")
public class ParamCode extends JJpaBaseModel {
	
	public ParamCode() {
	}
	
	public ParamCode(String type, String code, String name) {
		super();
		this.type = type;
		this.code = code;
		this.name = name;
	}

	public ParamCode(String type, String code, String name, String description) {
		super();
		this.type = type;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	private String type;
	
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
	

	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
