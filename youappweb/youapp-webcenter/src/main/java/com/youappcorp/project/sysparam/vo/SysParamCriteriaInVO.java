package com.youappcorp.project.sysparam.vo;

import me.bunny.app._c.data.web.model.JInputModel;

public class SysParamCriteriaInVO implements JInputModel {

	private String code;
	
	private String value;
	
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
