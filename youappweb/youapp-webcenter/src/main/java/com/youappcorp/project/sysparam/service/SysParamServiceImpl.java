package com.youappcorp.project.sysparam.service;

import j.jave.platform.webcomp.core.service.ServiceSupport;
import me.bunny.kernel.jave.model.JPage;
import me.bunny.kernel.jave.model.JSimplePageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;

@Service(value="sysParamServiceImpl.transation.jpa")
public class SysParamServiceImpl extends ServiceSupport implements SysParamService {

	@Autowired
	private InternalSysParamServiceImpl internalSysParamServiceImpl;
	
	@Override
	public void saveSysParam( SysParam sysParam) {
		try{
			String code=sysParam.getCode();
			if(exists( code)){
				throw new BusinessException("the system code ["+code+"] already exists .");
			}
			internalSysParamServiceImpl.saveOnly( sysParam);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public boolean exists( String code) {
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
	public void updateSysParam( SysParam sysParam) {
		try{
			
			SysParam dbSysParam=getSysParamById( sysParam.getId());
//			dbSysParam.setCode(sysParam.getCode());
			dbSysParam.setValue(sysParam.getValue());
			dbSysParam.setDesc(sysParam.getDesc());
			
			internalSysParamServiceImpl.updateOnly( dbSysParam);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void deleteSysParam( SysParam sysParam) {
		internalSysParamServiceImpl.delete( sysParam.getId());
	}

	@Override
	public void deleteSysParamById( String id) {
		internalSysParamServiceImpl.delete( id);
	}
	
	@Override
	public SysParam getSysParamById( String id) {
		return internalSysParamServiceImpl.getById( id);
	}

	@Override
	public JPage<SysParam> getSysParams(
			SysParamCriteriaInVO sysParamCriteriaInVO,
			JSimplePageable simplePageable) {
		return internalSysParamServiceImpl.singleEntityQuery()
		.conditionDefault().likes("code", sysParamCriteriaInVO.getCode())
		.likes("value", sysParamCriteriaInVO.getValue())
		.likes("desc", sysParamCriteriaInVO.getDesc())
		.ready()
		.order().asc("code").desc("updateTime")
		.ready()
		.modelPage(simplePageable);
	}

}
