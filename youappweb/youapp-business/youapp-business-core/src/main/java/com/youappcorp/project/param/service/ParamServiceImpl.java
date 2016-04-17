/**
 * 
 */
package com.youappcorp.project.param.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.spirngjpa.query.QueryBuilder;
import j.jave.platform.basicwebcomp.web.cache.resource.coderef.CodeRefCacheModelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.param.jpa.ParamCodeJPARepo;
import com.youappcorp.project.param.jpa.ParamTypeJPARepo;
import com.youappcorp.project.param.model.ParamCode;
import com.youappcorp.project.param.model.ParamCriteria;
import com.youappcorp.project.param.model.ParamType;
import com.youappcorp.project.websupport.model.CodeTableCacheModel;

/**
 * parameter basic service.
 * @author J
 */
@Service(value="paramService.transation.jpa")
public class ParamServiceImpl extends ServiceSupport implements ParamService{

	@Autowired
	private InternalParamTypeServiceImpl internalParamTypeServiceImpl;
	
	@Autowired
	private InternalParamCodeServiceImpl internalParamCodeServiceImpl;
	
	@Autowired
	private ParamCodeJPARepo paramCodeRepo;
	
	@Autowired
	private ParamTypeJPARepo paramTypeJPARepo;
	
	@Autowired
	private  CodeRefCacheModelService classd;
	
	@Override
	public void saveParam(ServiceContext context, ParamType paramType,
			ParamCode paramCode) throws JServiceException {
		saveParamType(context, paramType);
		paramCode.setTypeId(paramType.getId());
		internalParamCodeServiceImpl.saveOnly(context, paramCode);
	}

	@Override
	public void updateParam(ServiceContext context, ParamType paramType,
			ParamCode paramCode) throws JServiceException {
		internalParamTypeServiceImpl.updateOnly(context, paramType);
		internalParamCodeServiceImpl.updateOnly(context, paramCode);
	}

	@Override
	public List<CodeTableCacheModel> getCodeTableCacheModels(ServiceContext context) {
		String nativeSql=
				"SELECT PT.CODE TYPE, PC.CODE,PC.NAME from PARAM_CODE PC , PARAM_TYPE PT"
				+ " WHERE PC.TYPEID = PT.ID";
		List<CodeTableCacheModel> codes=QueryBuilder.get(getEntityManager()).setNativeSql(nativeSql)
				.setResultSetMapping("CodeTableQueryMapping")
		.build().execute();
		
		nativeSql=
				"SELECT PT.CODE TYPE, PT.CODE , PT.NAME from PARAM_TYPE PT";
		List<CodeTableCacheModel> types=QueryBuilder.get(getEntityManager()).setNativeSql(nativeSql)
				.setResultSetMapping("CodeTableQueryMapping")
		.build().execute();
		codes.addAll(types);
		return codes;
	}

	@Override
	public JIPersist<?, ?> getRepo() {
		return null;
	}

	@Override
	public void updateParamCode(ServiceContext context, ParamCode paramCode) throws JServiceException {
		
		String jpql="select count(1)  from ParamCode pc where pc.deleted='N'"
				+ " and pc.id<> :id and pc.code=:code and pc.typeId=:typeId";
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("id", paramCode.getId());
		params.put("code", paramCode.getCode());
		params.put("typeId", paramCode.getTypeId());
		
		long count=QueryBuilder.get(getEntityManager())
		.setJpql(jpql)
		.setParams(params)
		.build().execute();
		if(count>1){
			throw new JServiceException("param type already exists.");
		}
		
		internalParamCodeServiceImpl.updateOnly(context, paramCode);
	}

	@Override
	public void updateParamType(ServiceContext context, ParamType paramType) throws JServiceException{
		
		String jpql="select count(1) from ParamType p where p.deleted='N' "
				+ " and p.id <> :id and p.code =:code";
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("id", paramType.getId());
		params.put("code", paramType.getCode());
		
		long count=QueryBuilder.get(getEntityManager())
		.setJpql(jpql)
		.setParams(params)
		.build().execute();
		if(count>1){
			throw new JServiceException("param type already exists.");
		}
		internalParamTypeServiceImpl.updateOnly(context, paramType);
	}

	@Override
	public boolean existsParamType(ServiceContext context, String code) {
		long count=paramTypeJPARepo.getCountByCode(code);
		return count>0;
	}

	@Override
	public boolean existsParamCode(ServiceContext context, String type,String code) {
		long count=paramCodeRepo.getCountByTypeAndCode(type, code);
		return count>0;
	}

	@Override
	public void saveParamCode(ServiceContext context, ParamCode paramCode) throws JServiceException{
		if(existsParamCodeByTypeIdAndCode(context, paramCode.getTypeId(), paramCode.getCode())){
			throw new JServiceException("param code already exists.");
		}
		internalParamCodeServiceImpl.saveOnly(context, paramCode);
	}

	private boolean existsParamCodeByTypeIdAndCode(ServiceContext context, String typeId, String code){
		return paramCodeRepo.getCountByTypeIdAndCode(typeId, code)>0;
	}
	
	@Override
	public void saveParamType(ServiceContext context, ParamType paramType) throws JServiceException {
		if(existsParamType(context, paramType.getCode())){
			throw new JServiceException("param type already exists.");
		}
		internalParamTypeServiceImpl.saveOnly(context, paramType);
	}
	
	@Override
	public JPage<ParamType> getAllParamTypes(ServiceContext context,
			ParamCriteria paramCriteria) {
		return internalParamTypeServiceImpl.getsByPage(context, paramCriteria);
	}
	
	@Override
	public JPage<ParamCode> getAllParamCodes(ServiceContext context,
			ParamCriteria paramCriteria) {
		return internalParamCodeServiceImpl.getsByPage(context, paramCriteria);
	}
	
	@Override
	public JPage<ParamCode> getAllParamCodesByType(ServiceContext context,
			ParamCriteria paramCriteria) {
		String jpql="select pc from ParamCode pc , ParamType pt "
				+ " where pc.deleted='N' and pt.deleted='N' "
				+ "  and pc.typeId=pt.id  and pt.code =:code";
		Map<String , Object> params=new HashMap<String, Object>();
		params.put("code", paramCriteria.getCode());
		
		return QueryBuilder.get(getEntityManager())
		.setJpql(jpql)
		.setPageable(paramCriteria)
		.setParams(params)
		.build().execute();
	}
	
	
}
