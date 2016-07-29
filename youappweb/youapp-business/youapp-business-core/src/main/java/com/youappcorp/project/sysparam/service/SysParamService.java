package com.youappcorp.project.sysparam.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.platform.data.web.model.SimplePageRequest;
import j.jave.platform.webcomp.core.service.ServiceContext;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;

public interface SysParamService {

	void saveSysParam(ServiceContext serviceContext,SysParam sysParam);
	
	void updateSysParam(ServiceContext serviceContext,SysParam sysParam);
	
	void deleteSysParam(ServiceContext serviceContext,SysParam sysParam);
	
	void deleteSysParamById(ServiceContext serviceContext,String id);
	
	SysParam getSysParamById(ServiceContext serviceContext,String id);
	
	JPage<SysParam> getSysParams(ServiceContext serviceContext,SysParamCriteriaInVO sysParamCriteriaInVO, SimplePageRequest simplePageRequest);
	
	boolean exists(ServiceContext serviceContext,String code);
}
