package test.com.youappcorp.template.ftl.testmanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;

import test.com.youappcorp.template.ftl.testmanager.model.ParamCode;
import test.com.youappcorp.template.ftl.testmanager.model.ParamCodeRecord;
import test.com.youappcorp.template.ftl.testmanager.vo.ParamCodeCriteria;
import test.com.youappcorp.template.ftl.testmanager.model.ParamType;
import test.com.youappcorp.template.ftl.testmanager.model.ParamTypeRecord;
import test.com.youappcorp.template.ftl.testmanager.vo.ParamTypeCriteria;



public interface TestManagerService {

	/**
	 * save
	 */
	void saveParamCode (ParamCodeRecord paramCodeRecord);
	
	/**
	 * update
	 */
	void updateParamCode (ParamCodeRecord paramCodeRecord);
	
	/**
	 * delete
	 */
	void deleteParamCode (ParamCodeRecord paramCodeRecord);
	
	/**
	 * delete
	 */
	void deleteParamCodeById (String id);
	
	/**
	 * get
	 */
	ParamCodeRecord getParamCodeById (String id);
	
	/**
	 * page...
	 */
	JPage<ParamCodeRecord> getParamCodesByPage(ParamCodeCriteria paramCodeCriteria, JSimplePageable simplePageable);

	/**
	 * save
	 */
	void saveParamType (ParamTypeRecord paramTypeRecord);
	
	/**
	 * update
	 */
	void updateParamType (ParamTypeRecord paramTypeRecord);
	
	/**
	 * delete
	 */
	void deleteParamType (ParamTypeRecord paramTypeRecord);
	
	/**
	 * delete
	 */
	void deleteParamTypeById (String id);
	
	/**
	 * get
	 */
	ParamTypeRecord getParamTypeById (String id);
	
	/**
	 * page...
	 */
	JPage<ParamTypeRecord> getParamTypesByPage(ParamTypeCriteria paramTypeCriteria, JSimplePageable simplePageable);

	
}
