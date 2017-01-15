package com.youappcorp.project.usermanager.service;

import me.bunny.app._c._web.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.jpa.UserRoleJPARepo;
import com.youappcorp.project.usermanager.model.UserRole;

@Service(value="InternalUserRoleServiceImpl.transation.jpa")
public class InternalUserRoleServiceImpl extends InternalServiceSupport<UserRole>{

	@Autowired
	private UserRoleJPARepo userRoleJPARepo;
	
	@Override
	public JIPersist<?, UserRole, String> getRepo() {
		return userRoleJPARepo;
	}

}
