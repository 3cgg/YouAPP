package com.youappcorp.project.sysparam.model;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_sysparam")
public class SysParam  extends JJpaBaseModel{

	@Column(name = "_code")
	private String code;
	
	@Column(name = "_value")
	private String value;
	
	@Column(name = "_desc")
	private String desc;

	@Column(name = "_code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "_value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "_desc")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
