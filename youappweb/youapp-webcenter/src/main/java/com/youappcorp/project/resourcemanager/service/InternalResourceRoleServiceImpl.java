package com.youappcorp.project.resourcemanager.service;

import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import me.bunny.kernel.jave.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.resourcemanager.jpa.ResourceRoleJPARepo;
import com.youappcorp.project.resourcemanager.model.ResourceRole;


@Service(value="InternalResourceRoleServiceImpl.transation.jpa")
public class InternalResourceRoleServiceImpl extends InternalServiceSupport<ResourceRole>{

	@Autowired
	private ResourceRoleJPARepo resourceRoleJPARepo;
	
	@Override
	public JIPersist<?, ResourceRole, String> getRepo() {
		return resourceRoleJPARepo;
	}

}
