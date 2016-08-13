package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.jpa.GroupJPARepo;
import com.youappcorp.project.usermanager.model.Group;

@Service(value="InternalGroupServiceImpl.transation.jpa")
public class InternalGroupServiceImpl extends InternalServiceSupport<Group>{

	@Autowired
	private GroupJPARepo groupJPARepo;
	
	@Override
	public JIPersist<?, Group, String> getRepo() {
		return groupJPARepo;
	}

}
