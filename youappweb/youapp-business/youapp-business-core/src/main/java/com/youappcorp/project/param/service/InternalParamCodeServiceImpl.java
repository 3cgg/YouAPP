package com.youappcorp.project.param.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.param.jpa.ParamCodeJPARepo;
import com.youappcorp.project.param.model.ParamCode;

@Service(value="internalparamCodeService.transation.jpa")
public class InternalParamCodeServiceImpl extends ServiceSupport<ParamCode>{

	@Autowired
	private ParamCodeJPARepo paramCodeRepo;
	
	@Override
	public JIPersist<?, ParamCode> getRepo() {
		return paramCodeRepo;
	}

}
