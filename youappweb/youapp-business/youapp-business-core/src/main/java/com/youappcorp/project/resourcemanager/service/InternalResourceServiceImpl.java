package com.youappcorp.project.resourcemanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.resourcemanager.jpa.ResourceJPARepo;
import com.youappcorp.project.resourcemanager.model.Resource;


@Service(value="InternalResourceServiceImpl.transation.jpa")
public class InternalResourceServiceImpl extends InternalServiceSupport<Resource>{

	@Autowired
	private ResourceJPARepo resourceJPARepo;
	
	@Override
	public JIPersist<?, Resource, String> getRepo() {
		return resourceJPARepo;
	}

}
