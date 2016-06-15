package com.youappcorp.project.param.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.param.jpa.ParamCodeJPARepo;
import com.youappcorp.project.param.model.ParamCode;

@Service(value="internalparamCodeService.transation.jpa")
public class InternalParamCodeServiceImpl extends InternalServiceSupport<ParamCode>{

	@Autowired
	private ParamCodeJPARepo paramCodeRepo;
	
	@Override
	public JIPersist<?, ParamCode, String> getRepo() {
		return paramCodeRepo;
	}

}
