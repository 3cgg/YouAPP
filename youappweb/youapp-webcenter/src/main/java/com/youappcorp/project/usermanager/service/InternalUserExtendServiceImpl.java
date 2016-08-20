package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

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
