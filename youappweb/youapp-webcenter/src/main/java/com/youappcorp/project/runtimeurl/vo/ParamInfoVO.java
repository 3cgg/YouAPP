package com.youappcorp.project.runtimeurl.vo;

import j.jave.platform.data.web.model.JOutputModel;

public class ParamInfoVO implements JOutputModel {
	
	private String name;
	
	private String type;
	
	private int index;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
