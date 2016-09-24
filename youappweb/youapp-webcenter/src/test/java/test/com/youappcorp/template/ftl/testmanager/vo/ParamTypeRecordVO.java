package test.com.youappcorp.template.ftl.testmanager.vo;

import j.jave.kernal.jave.utils.JObjectUtils;

import test.com.youappcorp.template.ftl.testmanager.model.ParamTypeRecord;

public class ParamTypeRecordVO extends ParamTypeRecord {

	public ParamTypeRecordVO toParamTypeRecordVO(){
		return JObjectUtils.simpleCopy(this, ParamTypeRecordVO.class);
	}
	
	
}
