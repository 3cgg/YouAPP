/**
 * 
 */
package j.jave.framework.components.bill.model;

import j.jave.framework.model.JBaseModel;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */
public class Bill extends JBaseModel {
	
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
	private String desc;

	
	/*
	 * extend properties
	 */
	private String mallCodeName;
	
	private String goodTypeName;
	
	private String userCodeName;
	
	

	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserCodeName() {
		return userCodeName;
	}

	public void setUserCodeName(String userCodeName) {
		this.userCodeName = userCodeName;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	
}
