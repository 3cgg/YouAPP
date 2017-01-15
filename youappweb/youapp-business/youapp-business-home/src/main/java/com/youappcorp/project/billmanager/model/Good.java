/**
 * 
 */
package com.youappcorp.project.billmanager.model;

import me.bunny.app._c.data.web.model.JInputModel;
import me.bunny.app._c.data.web.model.JOutputModel;
import me.bunny.app._c.jpa.springjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 */
@Entity
@Table(name="GOOD")
public class Good extends JJpaBaseModel implements JInputModel , JOutputModel {

	private String billId;
	
	/**
	 * GOOD_NAME
	 */
	private String goodName;
	
	/**
	 * GOOD_TYPE
	 */
	private String goodType;
	
	private double money;
	
	/**
	 * optional description 
	 */
	private String description;
	
	
	@Column(name="BILL_ID")
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	@Column(name="MONEY")
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Column(name="GOOD_TYPE")
	public String getGoodType() {
		return goodType;
	}

	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}

		@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="GOOD_NAME")
	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	
}
