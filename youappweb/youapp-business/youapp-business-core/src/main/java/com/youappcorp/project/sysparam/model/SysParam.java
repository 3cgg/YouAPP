package com.youappcorp.project.sysparam.model;

import j.jave.kernal.jave.model.JBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_sysparam")
public class SysParam  extends JBaseModel{

	@Column(name = "_code")
	private String code;
	
	@Column(name = "_value")
	private String value;
	
	@Column(name = "_desc")
	private String desc;

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
	
}
