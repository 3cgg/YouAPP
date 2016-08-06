package com.youappcorp.project.sysparam.impl;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.service.SysParamService;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;

@Service(value="sysParamServiceImpl.transation.jpa")
public class SysParamServiceImpl extends ServiceSupport implements SysParamService {

	@Autowired
	private InternalSysParamServiceImpl internalSysParamServiceImpl;
	
	@Override
	public void saveSysParam(ServiceContext serviceContext, SysParam sysParam) {
		try{
			String code=sysParam.getCode();
			if(exists(serviceContext, code)){
				throw new BusinessException("the system code ["+code+"] already exists .");
			}
			internalSysParamServiceImpl.saveOnly(serviceContext, sysParam);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public boolean exists(ServiceContext serviceContext, String code) {
		try{
			SysParam param= internalSysParamServiceImpl.singleEntityQuery()
			.conditionDefault().equals("code", code).ready().model();
			return param!=null;
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return false;
	}
	
	@Override
	public void updateSysParam(ServiceContext serviceContext, SysParam sysParam) {
		try{
			
			SysParam dbSysParam=getSysParamById(serviceContext, sysParam.getId());
//			dbSysParam.setCode(sysParam.getCode());
			dbSysParam.setValue(sysParam.getValue());
			dbSysParam.setDesc(sysParam.getDesc());
			
			internalSysParamServiceImpl.updateOnly(serviceContext, dbSysParam);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void deleteSysParam(ServiceContext serviceContext, SysParam sysParam) {
		internalSysParamServiceImpl.delete(serviceContext, sysParam.getId());
	}

	@Override
	public void deleteSysParamById(ServiceContext serviceContext, String id) {
		internalSysParamServiceImpl.delete(serviceContext, id);
	}
	
	@Override
	public SysParam getSysParamById(ServiceContext serviceContext, String id) {
		return internalSysParamServiceImpl.getById(serviceContext, id);
	}

	@Override
	public JPage<SysParam> getSysParams(ServiceContext serviceContext,
			SysParamCriteriaInVO sysParamCriteriaInVO,
			JSimplePageable simplePageable) {
		return internalSysParamServiceImpl.singleEntityQuery()
		.conditionDefault().likes("code", sysParamCriteriaInVO.getCode())
		.likes("value", sysParamCriteriaInVO.getValue())
		.likes("desc", sysParamCriteriaInVO.getDesc())
		.ready().modelPage(simplePageable);
	}

}
