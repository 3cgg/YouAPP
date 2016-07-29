package com.youappcorp.project.sysparam.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kcfy.platform.server.BusinessException;
import com.kcfy.platform.server.BusinessExceptionUtil;
import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.service.SysParamService;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;
import com.kcfy.platform.server.kernal.model.JPage;
import com.kcfy.platform.server.kernal.model.SimplePageRequest;
import com.kcfy.platform.server.kernal.service.ServiceContext;
import com.kcfy.platform.server.kernal.service.ServiceSupport;

@Service
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
			SysParam param= internalSysParamServiceImpl.singleEntityQuery2()
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
		internalSysParamServiceImpl.delete(serviceContext, sysParam);
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
			SimplePageRequest simplePageRequest) {
		return internalSysParamServiceImpl.singleEntityQuery2()
		.conditionDefault().likes("code", sysParamCriteriaInVO.getCode())
		.likes("value", sysParamCriteriaInVO.getValue())
		.likes("desc", sysParamCriteriaInVO.getDesc())
		.ready().modelPage(simplePageRequest);
	}

}
