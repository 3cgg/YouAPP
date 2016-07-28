package com.youappcorp.project.bill.model;

import j.jave.platform.data.web.model.BaseCriteria;

import java.sql.Timestamp;

public class BillSearchCriteria extends BaseCriteria {

	/**
	 *用户CODE 
	 */
	private String userCode;
	
	private double money;
	
	/**
	 * GOOD_NAME
	 */
	private String goodName;
	
	/**
	 * GOOD_TYPE
	 */
	private String goodType;
	
	/**
	 * 购物地址编码
	 */
	private String mallCode;
	
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

	private int latestMonth;

	public int getLatestMonth() {
		return latestMonth;
	}

	public void setLatestMonth(int latestMonth) {
		this.latestMonth = latestMonth;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodType() {
		return goodType;
	}

	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}

	public String getMallCode() {
		return mallCode;
	}

	public void setMallCode(String mallCode) {
		this.mallCode = mallCode;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public Timestamp getBillTime() {
		return billTime;
	}

	public void setBillTime(Timestamp billTime) {
		this.billTime = billTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
