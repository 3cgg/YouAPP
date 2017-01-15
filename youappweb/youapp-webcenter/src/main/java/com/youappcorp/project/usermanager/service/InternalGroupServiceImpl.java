package com.youappcorp.project.usermanager.service;

import me.bunny.app._c._web.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

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
