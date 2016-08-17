/**
 * 
 */
package com.youappcorp.project.bill.model;

import j.jave.platform.data.web.model.JInputModel;
import j.jave.platform.data.web.model.JOutputModel;
import j.jave.platform.jpa.springjpa.JJpaBaseModel;
import j.jave.platform.webcomp.web.proext.annotation.CodeExtend;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youappcorp.project.bill.BillCodes.GoodTypeCode;
import com.youappcorp.project.bill.BillCodes.MallCode;

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
	 * GOOD_NAME
	 */
	private String goodName;
	
	/**
	 * GOOD_TYPE
	 */
	private String goodType;
	
	@CodeExtend(property="goodType",codeType=GoodTypeCode.CODE_TYPE)
	private String goodTypeName;
	
	/**
	 * 购物地址编码
	 */
	private String mallCode;
	
	/*
	 * extend properties
	 */
	@CodeExtend(property="mallCode",codeType=MallCode.CODE_TYPE)
	private String mallCodeName;
	
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

	@Transient
	public String getMallCodeName() {
		return mallCodeName;
	}

	public void setMallCodeName(String mallCodeName) {
		this.mallCodeName = mallCodeName;
	}

	@Transient
	public String getGoodTypeName() {
		return goodTypeName;
	}

	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
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

	@Column(name="MALL_CODE")
	public String getMallCode() {
		return mallCode;
	}

	public void setMallCode(String mallCode) {
		this.mallCode = mallCode;
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

	@Column(name="GOOD_NAME")
	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	
}
