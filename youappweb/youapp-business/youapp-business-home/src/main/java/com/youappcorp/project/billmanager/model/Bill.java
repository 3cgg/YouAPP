/**
 * 
 */
package com.youappcorp.project.billmanager.model;

import me.bunny.app._c.data.web.model.JInputModel;
import me.bunny.app._c.data.web.model.JOutputModel;
import me.bunny.app._c.jpa.springjpa.JJpaBaseModel;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author J
 */
@Entity
@Table(name="BILL")
public class Bill extends JJpaBaseModel implements JInputModel , JOutputModel {
	
	/**
	 *用户ID
	 */
	private String userId;
	
	private double money;
	
	/**
	 * BILL_NAME
	 */
	private String billName;
	
	/**
	 * BILL_TYPE
	 */
	private String billType;
	
	/**
	 * 购物地址
	 */
	private String mallName;
	
	/**
	 * 账单时间
	 */
	private Timestamp billTime;

	/**
	 * optional description 
	 */
	private String description;
	
	@Column(name="USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="MONEY")
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
	
	@Column(name="BILL_NAME")
	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	@Column(name="BILL_TYPE")
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}
	

	@Column(name="MALL_NAME")
	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	@Column(name="BILL_TIME")
	public Timestamp getBillTime() {
		return billTime;
	}

	public void setBillTime(Timestamp billTime) {
		this.billTime = billTime;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
