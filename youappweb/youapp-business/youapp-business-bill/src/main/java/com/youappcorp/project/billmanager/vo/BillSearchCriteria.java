package com.youappcorp.project.billmanager.vo;

import j.jave.platform.data.web.model.BaseCriteria;

public class BillSearchCriteria extends BaseCriteria {
	
	private String moneyStart;
	
	private String moneyEnd;
	
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
	private String billTimeStart;

	/**
	 * 账单时间
	 */
	private String billTimeEnd;
	
	/**
	 * optional description 
	 */
	private String description;

	public String getMoneyStart() {
		return moneyStart;
	}

	public void setMoneyStart(String moneyStart) {
		this.moneyStart = moneyStart;
	}

	public String getMoneyEnd() {
		return moneyEnd;
	}

	public void setMoneyEnd(String moneyEnd) {
		this.moneyEnd = moneyEnd;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public String getBillTimeStart() {
		return billTimeStart;
	}

	public void setBillTimeStart(String billTimeStart) {
		this.billTimeStart = billTimeStart;
	}

	public String getBillTimeEnd() {
		return billTimeEnd;
	}

	public void setBillTimeEnd(String billTimeEnd) {
		this.billTimeEnd = billTimeEnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
