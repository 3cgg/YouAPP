package test.com.youappcorp.template.ftl.testmanager.model;

import j.jave.kernal.jave.utils.JObjectUtils;

import test.com.youappcorp.template.ftl.testmanager.model.ParamType;

public class ParamTypeRecord extends ParamType {

	public ParamType toParamType(){
		return JObjectUtils.simpleCopy(this, ParamType.class);
	}
	
	
}
