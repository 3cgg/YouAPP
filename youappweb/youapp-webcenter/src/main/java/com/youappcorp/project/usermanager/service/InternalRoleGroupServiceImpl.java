package com.youappcorp.project.usermanager.service;

import me.bunny.app._c._web.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

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
