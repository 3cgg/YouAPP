package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.jpa.RoleGroupJPARepo;
import com.youappcorp.project.usermanager.model.RoleGroup;

@Service(value="InternalRoleGroupServiceImpl.transation.jpa")
public class InternalRoleGroupServiceImpl extends InternalServiceSupport<RoleGroup>{

	@Autowired
	private RoleGroupJPARepo roleGroupJPARepo;
	
	@Override
	public JIPersist<?, RoleGroup, String> getRepo() {
		return roleGroupJPARepo;
	}

}
