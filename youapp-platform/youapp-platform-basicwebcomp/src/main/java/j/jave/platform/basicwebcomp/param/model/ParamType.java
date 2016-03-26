/**
 * 
 */
package j.jave.platform.basicwebcomp.param.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

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
		// TODO Auto-generated constructor stub
	}
	
	public ParamType(String name,String code) {
		this.name=name;
		this.code=code;
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
