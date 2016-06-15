/**
 * 
 */
package com.youappcorp.project.param.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.param.model.ParamCode;
import com.youappcorp.project.param.model.ParamCriteria;
import com.youappcorp.project.param.model.ParamType;
import com.youappcorp.project.websupport.model.CodeTableCacheModel;

/**
 * @author J
 */
public interface ParamService{
	
	/**
	 * save param , including some validations. see {@link #exists(ServiceContext, ParamCode)}
	 * @param context 
	 * @param paramType
	 * @param paramCode
	 * @throws JServiceException
	 */
	public void saveParam(ServiceContext context, ParamType paramType, ParamCode paramCode) throws BusinessException;
	
	
	/**
	 * update param including some validations.  see {@link #exists(ServiceContext, ParamCode)}
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void updateParam(ServiceContext context, ParamType paramType, ParamCode paramCode) throws BusinessException;

	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<CodeTableCacheModel> getCodeTableCacheModels(ServiceContext context); 
	
	public void updateParamCode(ServiceContext context,ParamCode paramCode) throws BusinessException;
	
	public void updateParamType(ServiceContext context,ParamType paramType) throws BusinessException;
	
	public void saveParamCode(ServiceContext context,ParamCode paramCode) throws BusinessException;
	
	public void saveParamType(ServiceContext context,ParamType paramType) throws BusinessException;
	
	public boolean existsParamType(ServiceContext context,String code);
	
	public boolean existsParamCode(ServiceContext context,String type,String code);
	
	JPage<ParamType> getAllParamTypes(ServiceContext context,ParamCriteria paramCriteria);
	
	JPage<ParamCode> getAllParamCodes(ServiceContext context,ParamCriteria paramCriteria);
	
	JPage<ParamCode> getAllParamCodesByType(ServiceContext context,ParamCriteria paramCriteria);

}
