package com.youappcorp.project.sysparam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.bunny.app._c.jpa.springjpa.JJpaBaseModel;

@Entity
@Table(name = "SYS_PARAM")
public class SysParam  extends JJpaBaseModel{

	private String code;
	
	private String value;
	
	private String desc;

	@Column(name = "_CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "_VALUE")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "DESCRIPTION")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
