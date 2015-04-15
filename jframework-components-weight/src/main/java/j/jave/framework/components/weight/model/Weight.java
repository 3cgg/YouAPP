package j.jave.framework.components.weight.model;

import java.sql.Timestamp;

import j.jave.framework.model.JBaseModel;


/**
 * mapping to Weight 
 * @author J
 *
 */
public class Weight extends JBaseModel {

	/**
	 *用户名 
	 */
	private String userName;
	
	/**
	 * 体重
	 */
	private double weight;
	
	/**
	 * 记录时间
	 */
	private Timestamp recordTime;
	
	/**
	 * 描述
	 */
	private String desc;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
