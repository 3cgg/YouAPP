/**
 * 
 */
package j.jave.platform.basicwebcomp.param.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.repo.ParamCodeRepo;
import j.jave.platform.basicwebcomp.spirngjpa.query.QueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * parameter basic service.
 * @author J
 */
public class temp {
//	
//
//	public ParamServiceImpl2(){
//		System.out.println("----------ParamServiceImpl-------------------");
//	}
//	@Autowired
//	private ParamCodeRepo<?> paramMapper;
//	
//	@Override
//	public void saveParam(ServiceContext context, ParamCode param)
//			throws JServiceException {
//		
//		if(exists(context, param)){
//			throw new JServiceException("The param is existing, please change others.");
//		}
//		saveOnly(context, param);
//	}
//
//	@Override
//	public void updateParam(ServiceContext context, ParamCode param)
//			throws JServiceException {
//		if(exists(context, param)){
//			throw new JServiceException("The param is existing, please change others.");
//		}
//		updateOnly(context, param);
//	}
//
//	@Override
//	public ParamCode getParamById(ServiceContext context, String id) {
//		return getById(context, id);
//	}
//
//	@Override
//	public JPage<ParamCode> getParamsByPage(ServiceContext context, JPageable pagination) {
//		return getsByPage(context, pagination);
//	}
//
//	@Override
//	public ParamCode getParamByFunctionIdAndCode(ServiceContext context,
//			String functionId, String code) {
//		return paramMapper.getParamByTypeIdAndCode(functionId, code);
//	}
//
//	@Override
//	public List<ParamCode> getParamByFunctionId(ServiceContext context,
//			String functionId) {
//		return paramMapper.getParamByTypeId(functionId);
//	}
//
//	@Override
//	public JIPersist<?, ParamCode> getRepo() {
//		return paramMapper;
//	}
//	
//	@Override
//	public boolean exists(ServiceContext context, ParamCode param) {
//		
//		if(JStringUtils.isNullOrEmpty(param.getFunctionId())){
//			throw new IllegalArgumentException("Function id is null.");
//		}
//		if(JStringUtils.isNullOrEmpty(param.getCode())){
//			throw new IllegalArgumentException("code is null.");
//		}
//		ParamCode dbParam= getParamByFunctionIdAndCode(context, param.getFunctionId(), param.getCode());
//		
//		if(dbParam==null) return false;
//		
//		// status of inserting 
//		if(JStringUtils.isNullOrEmpty(param.getId())){
//			return dbParam!=null;
//		}
//		//status of updating or others.
//		else{
//			return !dbParam.getId().equals(param.getId());
//		}
//		
//	}
//
//	@Override
//	public JPage<ParamCode> getParamsByNameByPage(ServiceContext context,
//			JPageable pagination,String name) {
//		Page<ParamCode> obj=paramMapper.getParamsByNameByPage(
//				toPageRequest(pagination),name);
//		return toJPage(obj, pagination);
//	}
//	
//	@Override
//	public long countParam(ServiceContext context, ParamCode param) {
//		String jpql="select count(0) from Param p where p.name = :name ";
//		Map<String, Object> params=new HashMap<String, Object>();
//		params.put("name", param.getName());
//		return QueryBuilder.get(getEntityManager()).setJpql(jpql)
//		.setParams(params)
//		.setSingle(true)
//		.build().execute();
//	}
//	
//	@Override
//	public List<ParamCode> allParams(ServiceContext context, ParamCode param) {
//		
//		
//		String jpql="from Param p where p.name = :name ";
//		Map<String, Object> params=new HashMap<String, Object>();
//		params.put("name", param.getName());
//		JPageRequest pageRequest= new JPageRequest();
//		pageRequest.setPageNumber(100);
//		
//		try{
//			String nativeSql="select p.NAME , P.CODE from PARAM p  where p.NAME = :name";
//			Object obj=QueryBuilder.get(getEntityManager()).setNativeSql(nativeSql)
////					.setCountNativeSql(countSql)
////					.setResult(Param.class)
//					.setResultSetMapping("ParamQueryMapping")
//			.setParams(params)
//			.setPageable(pageRequest)
//			.build().execute();
//			System.out.println(obj);
//		}catch(Exception e){
//			e.printStackTrace();	
//		}
//		
//		
//		
//		JPage<ParamCode> page= 
//				QueryBuilder.get(getEntityManager()).setJpql(jpql)
//				.setParams(params)
//				.setPageable(pageRequest)
//				.build().execute();
//		return page.getContent();
//	}
//	
//	@Override
//	public List<ParamCode> getAllParams(ServiceContext context) {
//		return paramMapper.getAllModels();
//	}
//	
}
