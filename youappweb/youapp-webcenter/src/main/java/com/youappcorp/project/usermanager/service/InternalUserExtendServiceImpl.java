package com.youappcorp.project.usermanager.service;

import me.bunny.app._c._web.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.jpa.UserExtendJPARepo;
import com.youappcorp.project.usermanager.model.UserExtend;

@Service(value="InternalUserExtendServiceImpl.transation.jpa")
public class InternalUserExtendServiceImpl extends InternalServiceSupport<UserExtend>{

	@Autowired
	private UserExtendJPARepo userExtendJPARepo;
	
	@Override
	public JIPersist<?, UserExtend, String> getRepo() {
		return userExtendJPARepo;
	}

}
