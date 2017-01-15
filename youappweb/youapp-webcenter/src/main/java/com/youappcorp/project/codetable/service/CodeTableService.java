/**
 * 
 */
package com.youappcorp.project.codetable.service;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.codetable.model.ParamCode;
import com.youappcorp.project.codetable.model.ParamType;
import com.youappcorp.project.codetable.vo.ParamCriteriaInVO;
import com.youappcorp.project.websupport.model.CodeTableCacheModel;

import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel.eventdriven.exception.JServiceException;

/**
 * @author J
 */
public interface CodeTableService{
	
	/**
	 * save param , including some validations. see {@link #exists()}
	 *  
	 * @param paramType
	 * @param paramCode
	 * @throws JServiceException
	 */
	public void saveParam( ParamType paramType, ParamCode paramCode) throws BusinessException;
	
	
	/**
	 * update param including some validations.  see {@link #exists(ParamCode)}
	 * 
	 * @param user
	 * @throws JServiceException
	 */
	public void updateParam( ParamType paramType, ParamCode paramCode) throws BusinessException;

	/**
	 * 
	 * 
	 * @return
	 */
	public List<CodeTableCacheModel> getCodeTableCacheModels(); 
	
	public void updateParamCode(ParamCode paramCode) throws BusinessException;
	
	public void updateParamType(ParamType paramType) throws BusinessException;
	
	public void saveParamCode(ParamCode paramCode) throws BusinessException;
	
	public void saveParamType(ParamType paramType) throws BusinessException;
	
	public boolean existsParamType(String code);
	
	public boolean existsParamCode(String type,String code);
	
	JPage<ParamType> getAllParamTypesByPage(ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable);
	
	JPage<ParamCode> getAllParamCodesByPage(ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable);
	
	JPage<ParamCode> getAllParamCodesByTypeByPage(String type,JSimplePageable simplePageable);

	List<ParamType> getAllParamTypes(ParamCriteriaInVO paramCriteria);
	
	List<ParamCode> getAllParamCodes(ParamCriteriaInVO paramCriteria);
	
	List<ParamCode> getAllParamCodesByType(String type);
	
	ParamType getParamTypeById(String id);
	
	ParamCode getParamCodeById(String id);

	void deleteParamTypeById(String id);
	
	void deleteParamCodeById(String id);
	
	
}
