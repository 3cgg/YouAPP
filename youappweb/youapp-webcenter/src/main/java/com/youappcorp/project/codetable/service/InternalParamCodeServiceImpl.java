package com.youappcorp.project.codetable.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.codetable.jpa.ParamCodeJPARepo;
import com.youappcorp.project.codetable.model.ParamCode;

@Service(value="internalParamCodeService.transation.jpa")
public class InternalParamCodeServiceImpl extends InternalServiceSupport<ParamCode>{

	@Autowired
	private ParamCodeJPARepo paramCodeRepo;
	
	@Override
	public JIPersist<?, ParamCode, String> getRepo() {
		return paramCodeRepo;
	}

}
