package com.youappcorp.project.sysparam.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;

public interface SysParamService {

	void saveSysParam(ServiceContext serviceContext,SysParam sysParam);
	
	void updateSysParam(ServiceContext serviceContext,SysParam sysParam);
	
	void deleteSysParam(ServiceContext serviceContext,SysParam sysParam);
	
	void deleteSysParamById(ServiceContext serviceContext,String id);
	
	SysParam getSysParamById(ServiceContext serviceContext,String id);
	
	JPage<SysParam> getSysParams(ServiceContext serviceContext,SysParamCriteriaInVO sysParamCriteriaInVO, JSimplePageable simplePageable);
	
	boolean exists(ServiceContext serviceContext,String code);
}
