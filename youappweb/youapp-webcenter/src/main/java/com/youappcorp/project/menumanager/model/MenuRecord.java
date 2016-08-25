package com.youappcorp.project.menumanager.model;

import j.jave.kernal.jave.support.treeview.JSimpleTreeStrcture;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JObjectUtils;

import java.util.Map;

public class MenuRecord extends Menu implements JSimpleTreeStrcture {

	
	public Menu toMenu(){
		return JObjectUtils.simpleCopy(this, Menu.class);
	}

	@Override
	public String getParentId() {
		return getPid();
	}
	
	@Override
	public Map<String, Object> toMap() {
		return JCollectionUtils.toMap(this);
	}
	
}
