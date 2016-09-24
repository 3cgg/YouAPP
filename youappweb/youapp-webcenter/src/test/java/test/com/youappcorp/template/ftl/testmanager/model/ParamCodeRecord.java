package test.com.youappcorp.template.ftl.testmanager.model;

import j.jave.kernal.jave.utils.JObjectUtils;

import test.com.youappcorp.template.ftl.testmanager.model.ParamCode;

public class ParamCodeRecord extends ParamCode {

	public ParamCode toParamCode(){
		return JObjectUtils.simpleCopy(this, ParamCode.class);
	}
	
	
}
