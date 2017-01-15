package com.youappcorp.project.extapp.extappsample.vo;

import me.bunny.app._c.data.web.model.JInputModel;
import me.bunny.app._c.data.web.model.JOutputModel;

public class SampleRecordVO implements JInputModel , JOutputModel {

	private String id;
	
	private String name;
	
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
