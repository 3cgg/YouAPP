/**
 * 
 */
package j.jave.framework.components.bill.model;

import j.jave.framework.model.JBaseModel;
import j.jave.framework.model.support.JColumn;
import j.jave.framework.model.support.JSQLType;
import j.jave.framework.model.support.JTable;

import java.sql.Timestamp;

/**
 * @author J
 */
@JTable(name="BILL")
public class Bill extends JBaseModel {
	
	/**
	 *用户CODE 
	 */
	@JColumn(name="USER_CODE",type=JSQLType.VARCHAR,length=32)
	private String userCode;
	
	@JColumn(name="MONEY",type=JSQLType.DOUBLE)
	private double money;
	
	/**
	 * GOOD_NAME
	 */
	@JColumn(name="GOOD_NAME",type=JSQLType.VARCHAR,length=64)
	private String goodName;
	
	/**
	 * GOOD_TYPE
	 */
	@JColumn(name="GOOD_TYPE",type=JSQLType.VARCHAR,length=32)
	private String goodType;
	
	/**
	 * 购物地址编码
	 */
	@JColumn(name="MALL_CODE",type=JSQLType.VARCHAR,length=32)
	private String mallCode;
	
	/**
	 * 购物地址
	 */
	@JColumn(name="MALL_NAME",type=JSQLType.VARCHAR,length=256)
	private String mallName;
	
	/**
	 * 账单时间
	 */
	@JColumn(name="BILL_TIME",type=JSQLType.TIMESTAMP)
	private Timestamp billTime;

	/**
	 * optional description 
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=512)
	private String description;

	
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
