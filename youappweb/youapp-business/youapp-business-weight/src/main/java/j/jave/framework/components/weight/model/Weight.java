package j.jave.framework.components.weight.model;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.model.support.JColumn;
import j.jave.framework.commons.model.support.JSQLType;
import j.jave.framework.commons.model.support.JTable;

import java.sql.Timestamp;


/**
 * mapping to Weight 
 * @author J
 *
 */
@JTable(name="WEIGHT")
public class Weight extends JBaseModel {

	/**
	 *用户名 
	 */
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	private String userName;
	
	/**
	 * 体重
	 */
	@JColumn(name="WEIGHT",type=JSQLType.DOUBLE)
	private double weight;
	
	/**
	 * 记录时间
	 */
	@JColumn(name="WEIGHT",type=JSQLType.TIMESTAMP)
	private Timestamp recordTime;
	
	/**
	 * 描述
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=512)
	private String description;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
}
