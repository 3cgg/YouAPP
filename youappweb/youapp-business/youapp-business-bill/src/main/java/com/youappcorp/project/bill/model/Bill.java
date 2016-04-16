/**
 * 
 */
package com.youappcorp.project.bill.model;

import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;
import j.jave.platform.basicwebcomp.web.proext.annotation.CodeExtend;

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
public class Bill extends JJpaBaseModel {
	
	/**
	 *用户ID
	 */
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="MONEY")
	private double money;
	
	/**
	 * GOOD_NAME
	 */
	@Column(name="GOOD_NAME")
	private String goodName;
	
	/**
	 * GOOD_TYPE
	 */
	@Column(name="GOOD_TYPE")
	private String goodType;
	
	@CodeExtend(property="goodType",codeType=GoodTypeCode.CODE_TYPE)
	@Transient
	private String goodTypeName;
	
	/**
	 * 购物地址编码
	 */
	@Column(name="MALL_CODE")
	private String mallCode;
	
	/*
	 * extend properties
	 */
	@CodeExtend(property="mallCode",codeType=MallCode.CODE_TYPE)
	@Transient
	private String mallCodeName;
	
	/**
	 * 购物地址
	 */
	@Column(name="MALL_NAME")
	private String mallName;
	
	/**
	 * 账单时间
	 */
	@Column(name="BILL_TIME")
	private Timestamp billTime;

	/**
	 * optional description 
	 */
	@Column(name="DESCRIPTION")
	private String description;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMallCodeName() {
		return mallCodeName;
	}

	public void setMallCodeName(String mallCodeName) {
		this.mallCodeName = mallCodeName;
	}

	public String getGoodTypeName() {
		return goodTypeName;
	}

	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
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

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	
}
