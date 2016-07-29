package com.youappcorp.project.sysparam.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.repo.SysParamRepo;
import com.kcfy.platform.server.kernal.repo.SingleEntityRepo;
import com.kcfy.platform.server.kernal.service.InternalServiceSupport;

@Service
public class InternalSysParamServiceImpl extends InternalServiceSupport<SysParam>{

	@Autowired
	private SysParamRepo sysParamRepo;
	
	@Override
	public SingleEntityRepo<SysParam, String> getRepo() {
		return sysParamRepo;
	}

}
