package com.youappcorp.project.usermanager.service;

import me.bunny.app._c._web.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.jpa.UserGroupJPARepo;
import com.youappcorp.project.usermanager.model.UserGroup;

@Service(value="InternalUserGroupServiceImpl.transation.jpa")
public class InternalUserGroupServiceImpl extends InternalServiceSupport<UserGroup>{

	@Autowired
	private UserGroupJPARepo userGroupJPARepo;
	
	@Override
	public JIPersist<?, UserGroup, String> getRepo() {
		return userGroupJPARepo;
	}

}
