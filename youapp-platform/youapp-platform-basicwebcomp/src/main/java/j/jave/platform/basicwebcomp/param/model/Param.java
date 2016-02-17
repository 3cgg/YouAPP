/**
 * 
 */
package j.jave.platform.basicwebcomp.param.model;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Administrator
 *
 */
@JTable(name="PARAM")
@Entity
@Table(name="PARAM")
public class Param extends JBaseModel {
	
	/**
	 * the id the type of funciton. 
	 */
	@JColumn(name="FUNCTIONID",type=JSQLType.VARCHAR,length=32)
	private String functionId;
	
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

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
    @Column(name = "ID")
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	
}
