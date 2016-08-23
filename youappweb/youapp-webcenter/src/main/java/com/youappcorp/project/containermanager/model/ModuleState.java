package com.youappcorp.project.containermanager.model;

import j.jave.kernal.jave.model.JModel;

public class ModuleState implements JModel {

	private String jarUrl;
	
	private boolean active;
	
	private long time;
	
	public String getJarUrl() {
		return jarUrl;
	}

	public void setJarUrl(String jarUrl) {
		this.jarUrl = jarUrl;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}
