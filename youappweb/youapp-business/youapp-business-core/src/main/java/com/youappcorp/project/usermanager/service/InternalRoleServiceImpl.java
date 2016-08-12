package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.jpa.RoleJPARepo;
import com.youappcorp.project.usermanager.model.Role;

@Service(value="InternalRoleServiceImpl.transation.jpa")
public class InternalRoleServiceImpl extends InternalServiceSupport<Role>{

	@Autowired
	private RoleJPARepo roleJPARepo;
	
	@Override
	public JIPersist<?, Role, String> getRepo() {
		return roleJPARepo;
	}

}
