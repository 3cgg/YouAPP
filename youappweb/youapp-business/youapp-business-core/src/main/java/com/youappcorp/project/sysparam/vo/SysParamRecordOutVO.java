package com.youappcorp.project.sysparam.vo;

import java.util.Date;

public class SysParamRecordOutVO {

	private String id;
	
	private String code;
	
	private String value;
	
	private String desc;
	
	private Date createDate;
	
	private String createDateStr;
	
	private Date modifyDate;
	
	private String modifyDateStr;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getModifyDateStr() {
		return modifyDateStr;
	}
	
	public String getCreateDateStr() {
		return createDateStr;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public void setModifyDateStr(String modifyDateStr) {
		this.modifyDateStr = modifyDateStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

	
}
