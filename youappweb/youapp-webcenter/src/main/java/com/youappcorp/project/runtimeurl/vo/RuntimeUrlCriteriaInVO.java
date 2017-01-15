package com.youappcorp.project.runtimeurl.vo;

import me.bunny.app._c.data.web.model.JInputModel;

public class RuntimeUrlCriteriaInVO implements JInputModel {

	private String url;
	
	private String name;
	
	private String desc;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
