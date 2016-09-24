package test.com.youappcorp.template.ftl.testmanager.vo;

import j.jave.kernal.jave.utils.JObjectUtils;

import test.com.youappcorp.template.ftl.testmanager.model.ParamCodeRecord;

public class ParamCodeRecordVO extends ParamCodeRecord {

	public ParamCodeRecordVO toParamCodeRecordVO(){
		return JObjectUtils.simpleCopy(this, ParamCodeRecordVO.class);
	}
	
	
}
