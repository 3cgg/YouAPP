package com.youappcorp.project.sysparam.impl;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.repo.SysParamRepo;

@Service(value="internalSysParamServiceImpl.transation.jpa")
public class InternalSysParamServiceImpl extends InternalServiceSupport<SysParam>{

	@Autowired
	private SysParamRepo sysParamRepo;

	@Override
	public JIPersist<?, SysParam, String> getRepo() {
		return sysParamRepo;
	}
	

}
