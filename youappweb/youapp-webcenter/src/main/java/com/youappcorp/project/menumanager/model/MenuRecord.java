package com.youappcorp.project.menumanager.model;

import java.util.Map;

import me.bunny.kernel.jave.support.treeview.JSimpleTreeStrcture;
import me.bunny.kernel.jave.utils.JCollectionUtils;
import me.bunny.kernel.jave.utils.JObjectUtils;

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
