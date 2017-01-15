package com.youappcorp.project.menumanager.service;

import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import me.bunny.kernel.jave.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.menumanager.jpa.MenuGroupJPARepo;
import com.youappcorp.project.menumanager.model.MenuGroup;

@Service(value="internalMenuGroupServiceImpl.transation.jpa")
public class InternalMenuGroupServiceImpl extends InternalServiceSupport<MenuGroup> {
	@Autowired
	private MenuGroupJPARepo menuGroupRepo;
	
	@Override
	public JIPersist<?, MenuGroup, String> getRepo() {
		return menuGroupRepo;
	}
}
