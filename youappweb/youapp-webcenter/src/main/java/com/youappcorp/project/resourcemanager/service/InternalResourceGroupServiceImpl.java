package com.youappcorp.project.resourcemanager.service;

import me.bunny.app._c._web.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.resourcemanager.jpa.ResourceGroupJPARepo;
import com.youappcorp.project.resourcemanager.model.ResourceGroup;


@Service(value="InternalResourceGroupServiceImpl.transation.jpa")
public class InternalResourceGroupServiceImpl extends InternalServiceSupport<ResourceGroup>{

	@Autowired
	private ResourceGroupJPARepo resourceGroupJPARepo;
	
	@Override
	public JIPersist<?, ResourceGroup, String> getRepo() {
		return resourceGroupJPARepo;
	}

}
