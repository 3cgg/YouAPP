package com.youappcorp.project.param.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.param.jpa.ParamTypeJPARepo;
import com.youappcorp.project.param.model.ParamType;

@Service(value="internalparamTypeService.transation.jpa")
public class InternalParamTypeServiceImpl extends InternalServiceSupport<ParamType>{

	@Autowired
	private ParamTypeJPARepo paramTypeJPARepo;
	
	@Override
	public JIPersist<?, ParamType, String> getRepo() {
		return paramTypeJPARepo;
	}
	
}
