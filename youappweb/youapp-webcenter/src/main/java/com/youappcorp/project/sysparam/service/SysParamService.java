package com.youappcorp.project.sysparam.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;

public interface SysParamService {

	void saveSysParam(SysParam sysParam);
	
	void updateSysParam(SysParam sysParam);
	
	void deleteSysParam(SysParam sysParam);
	
	void deleteSysParamById(String id);
	
	SysParam getSysParamById(String id);
	
	JPage<SysParam> getSysParams(SysParamCriteriaInVO sysParamCriteriaInVO, JSimplePageable simplePageable);
	
	boolean exists(String code);
}
