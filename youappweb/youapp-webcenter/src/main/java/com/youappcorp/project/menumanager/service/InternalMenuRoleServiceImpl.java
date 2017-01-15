package com.youappcorp.project.menumanager.service;

import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import me.bunny.kernel.jave.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.menumanager.jpa.MenuRoleJPARepo;
import com.youappcorp.project.menumanager.model.MenuRole;

@Service(value="internalMenuRoleServiceImpl.transation.jpa")
public class InternalMenuRoleServiceImpl extends InternalServiceSupport<MenuRole> {
	@Autowired
	private MenuRoleJPARepo menuRoleRepo;
	
	@Override
	public JIPersist<?, MenuRole, String> getRepo() {
		return menuRoleRepo;
	}
}
