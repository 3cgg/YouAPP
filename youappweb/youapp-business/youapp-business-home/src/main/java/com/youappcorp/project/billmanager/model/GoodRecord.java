package com.youappcorp.project.billmanager.model;

import java.sql.Timestamp;

import me.bunny.kernel._c.utils.JObjectUtils;

public class GoodRecord extends Good {

	/**
	 * 购物地址
	 */
	private String mallName;
	
	/**
	 * 账单时间
	 */
	private Timestamp billTime;
	
	private String userId;
	
	private String userName;
	
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




	public String getUserId() {
		return userId;
	}




	public void setUserId(String userId) {
		this.userId = userId;
	}




	public String getUserName() {
		return userName;
	}




	public void setUserName(String userName) {
		this.userName = userName;
	}




	public Good toGood(){
		return JObjectUtils.simpleCopy(this, Good.class);
	}
	
	
}
