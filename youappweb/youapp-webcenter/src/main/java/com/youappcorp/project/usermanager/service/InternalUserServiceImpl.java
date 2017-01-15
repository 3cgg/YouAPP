package com.youappcorp.project.usermanager.service;

import me.bunny.app._c._web.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.jpa.UserJPARepo;
import com.youappcorp.project.usermanager.model.User;

@Service(value="InternalUserServiceImpl.transation.jpa")
public class InternalUserServiceImpl extends InternalServiceSupport<User>{

	@Autowired
	private UserJPARepo userJPARepo;
	
	@Override
	public JIPersist<?, User, String> getRepo() {
		return userJPARepo;
	}

}
