package com.youappcorp.project.codetable.service;

import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import me.bunny.kernel._c.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.codetable.jpa.ParamTypeJPARepo;
import com.youappcorp.project.codetable.model.ParamType;

@Service(value="internalParamTypeService.transation.jpa")
public class InternalParamTypeServiceImpl extends InternalServiceSupport<ParamType>{

	@Autowired
	private ParamTypeJPARepo paramTypeJPARepo;
	
	@Override
	public JIPersist<?, ParamType, String> getRepo() {
		return paramTypeJPARepo;
	}
	
}
