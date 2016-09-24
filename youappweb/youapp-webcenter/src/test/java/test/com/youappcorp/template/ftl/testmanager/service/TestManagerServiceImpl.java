package test.com.youappcorp.template.ftl.testmanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.jpa.springjpa.query.JCondition.Condition;
import j.jave.platform.jpa.springjpa.query.JJpaDateParam;
import j.jave.platform.jpa.springjpa.query.JQuery;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import test.com.youappcorp.template.ftl.testmanager.model.ParamCode;
import test.com.youappcorp.template.ftl.testmanager.model.ParamCodeRecord;
import test.com.youappcorp.template.ftl.testmanager.vo.ParamCodeCriteria;
import test.com.youappcorp.template.ftl.testmanager.service.InternalParamCodeServiceImpl;
import test.com.youappcorp.template.ftl.testmanager.model.ParamType;
import test.com.youappcorp.template.ftl.testmanager.model.ParamTypeRecord;
import test.com.youappcorp.template.ftl.testmanager.vo.ParamTypeCriteria;
import test.com.youappcorp.template.ftl.testmanager.service.InternalParamTypeServiceImpl;

import test.com.youappcorp.template.ftl.testmanager.service.TestManagerService;

public class TestManagerServiceImpl extends ServiceSupport implements TestManagerService {

	@Autowired
	private InternalParamCodeServiceImpl internalParamCodeServiceImpl;

	@Autowired
	private InternalParamTypeServiceImpl internalParamTypeServiceImpl;


	/**
	 * save
	 */
	@Override
	public void saveParamCode (ParamCodeRecord paramCodeRecord){
		try{
			ParamCode paramCode=paramCodeRecord.toParamCode();
			internalParamCodeServiceImpl.saveOnly( paramCode);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * update
	 */
	@Override
	public void updateParamCode (ParamCodeRecord paramCodeRecord){
		try{
			ParamCode paramCode=paramCodeRecord.toParamCode();
			ParamCode dbParamCode=internalParamCodeServiceImpl.getById( paramCode.getId());
			
			dbParamCode.setType(paramCode.getType());
			dbParamCode.setCode(paramCode.getCode());
			dbParamCode.setName(paramCode.getName());
			dbParamCode.setDescription(paramCode.getDescription());
			internalParamCodeServiceImpl.updateOnly( dbParamCode);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * delete
	 */
	@Override
	public void deleteParamCode (ParamCodeRecord paramCodeRecord){
		internalParamCodeServiceImpl.delete(paramCodeRecord.toParamCode().getId());
	}
	
	/**
	 * delete
	 */
	@Override
	public void deleteParamCodeById (String id){
		internalParamCodeServiceImpl.delete( id);
	}
	
	
	
	private JQuery<?> buildParamCodeQuery(
			 Map<String, Condition> params){
		String jpql="select a. id as id "
				+ ", a.type as type "
				+ ", a.code as code "
				+ ", a.name as name "
				+ ", a.description as description "
				+ ", a.createId as createId "
				+ ", a.updateId as updateId "
				+ ", a.createTime as createTime "
				+ ", a.updateTime as updateTime "
				+ ", a.deleted as deleted "
				+ ", a.version as version "
				+ " from ParamCode a "
				+ " where a.deleted='N' ";
		Condition condition=null;		 
		if((condition=params.get("type"))!=null){
			jpql=jpql+ " and a.type "+condition.getOpe()+" :type";
		}
		if((condition=params.get("code"))!=null){
			jpql=jpql+ " and a.code "+condition.getOpe()+" :code";
		}
		if((condition=params.get("name"))!=null){
			jpql=jpql+ " and a.name "+condition.getOpe()+" :name";
		}
		if((condition=params.get("description"))!=null){
			jpql=jpql+ " and a.description "+condition.getOpe()+" :description";
		}
		if((condition=params.get("id"))!=null){
			jpql=jpql+ " and a.id "+condition.getOpe()+" :id";
		}
		if((condition=params.get("createId"))!=null){
			jpql=jpql+ " and a.createId "+condition.getOpe()+" :createId";
		}
		if((condition=params.get("updateId"))!=null){
			jpql=jpql+ " and a.updateId "+condition.getOpe()+" :updateId";
		}
		if((condition=params.get("createTimeStart"))!=null){
			jpql=jpql+ " and a.createTime "+condition.getOpe()+" :createTimeStart";
		}
		if((condition=params.get("createTimeEnd"))!=null){
			jpql=jpql+ " and a.createTime "+condition.getOpe()+" :createTimeEnd";
		}
		if((condition=params.get("createTime"))!=null){
			jpql=jpql+ " and a.createTime "+condition.getOpe()+" :createTime";
		}
		if((condition=params.get("updateTimeStart"))!=null){
			jpql=jpql+ " and a.updateTime "+condition.getOpe()+" :updateTimeStart";
		}
		if((condition=params.get("updateTimeEnd"))!=null){
			jpql=jpql+ " and a.updateTime "+condition.getOpe()+" :updateTimeEnd";
		}
		if((condition=params.get("updateTime"))!=null){
			jpql=jpql+ " and a.updateTime "+condition.getOpe()+" :updateTime";
		}
		if((condition=params.get("deleted"))!=null){
			jpql=jpql+ " and a.deleted "+condition.getOpe()+" :deleted";
		}
		if((condition=params.get("version"))!=null){
			jpql=jpql+ " and a.version "+condition.getOpe()+" :version";
		}
		jpql=jpql+" order by a.updateTime desc";	
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params)); 
	}
	
	/**
	 * get
	 */
	@Override
	public ParamCodeRecord getParamCodeById (String id){
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(id));
		return buildParamCodeQuery( params)
				.model(ParamCodeRecord.class);
	}
	
	
	private Map<String, Condition> getParams(ParamCodeCriteria paramCodeCriteria) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		String type=paramCodeCriteria.getType();
		if(JStringUtils.isNotNullOrEmpty(type)){
			params.put("type", Condition.likes(type));
		}
		String code=paramCodeCriteria.getCode();
		if(JStringUtils.isNotNullOrEmpty(code)){
			params.put("code", Condition.likes(code));
		}
		String name=paramCodeCriteria.getName();
		if(JStringUtils.isNotNullOrEmpty(name)){
			params.put("name", Condition.likes(name));
		}
		String description=paramCodeCriteria.getDescription();
		if(JStringUtils.isNotNullOrEmpty(description)){
			params.put("description", Condition.likes(description));
		}
		String id=paramCodeCriteria.getId();
		if(JStringUtils.isNotNullOrEmpty(id)){
			params.put("id", Condition.likes(id));
		}
		String createId=paramCodeCriteria.getCreateId();
		if(JStringUtils.isNotNullOrEmpty(createId)){
			params.put("createId", Condition.likes(createId));
		}
		String updateId=paramCodeCriteria.getUpdateId();
		if(JStringUtils.isNotNullOrEmpty(updateId)){
			params.put("updateId", Condition.likes(updateId));
		}
		String createTimeStart=paramCodeCriteria.getCreateTimeStart();
		if(JStringUtils.isNotNullOrEmpty(createTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTimeStart", Condition.larger(dateParam));
		}
		String createTimeEnd=paramCodeCriteria.getCreateTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(createTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTimeEnd", Condition.smaller(dateParam));
		}
		String createTime=paramCodeCriteria.getCreateTime();
		if(JStringUtils.isNotNullOrEmpty(createTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTime", Condition.equal(dateParam));
		}
		String updateTimeStart=paramCodeCriteria.getUpdateTimeStart();
		if(JStringUtils.isNotNullOrEmpty(updateTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTimeStart", Condition.larger(dateParam));
		}
		String updateTimeEnd=paramCodeCriteria.getUpdateTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(updateTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTimeEnd", Condition.smaller(dateParam));
		}
		String updateTime=paramCodeCriteria.getUpdateTime();
		if(JStringUtils.isNotNullOrEmpty(updateTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTime", Condition.equal(dateParam));
		}
		String deleted=paramCodeCriteria.getDeleted();
		if(JStringUtils.isNotNullOrEmpty(deleted)){
			params.put("deleted", Condition.likes(deleted));
		}
		String version=paramCodeCriteria.getVersion();
		if(JStringUtils.isNotNullOrEmpty(version)){
			params.put("version", Condition.equal(version));
		}
		return params;
	}
	
	
	
	/**
	 * page...
	 */
	@Override
	public JPage<ParamCodeRecord> getParamCodesByPage(ParamCodeCriteria paramCodeCriteria, JSimplePageable simplePageable){
		Map<String, Condition> params = getParams(paramCodeCriteria);
		return buildParamCodeQuery( params)
				.setPageable(simplePageable)
				.modelPage(ParamCodeRecord.class);
	}

	/**
	 * save
	 */
	@Override
	public void saveParamType (ParamTypeRecord paramTypeRecord){
		try{
			ParamType paramType=paramTypeRecord.toParamType();
			internalParamTypeServiceImpl.saveOnly( paramType);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * update
	 */
	@Override
	public void updateParamType (ParamTypeRecord paramTypeRecord){
		try{
			ParamType paramType=paramTypeRecord.toParamType();
			ParamType dbParamType=internalParamTypeServiceImpl.getById( paramType.getId());
			
			dbParamType.setCode(paramType.getCode());
			dbParamType.setName(paramType.getName());
			dbParamType.setDescription(paramType.getDescription());
			internalParamTypeServiceImpl.updateOnly( dbParamType);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * delete
	 */
	@Override
	public void deleteParamType (ParamTypeRecord paramTypeRecord){
		internalParamTypeServiceImpl.delete(paramTypeRecord.toParamType().getId());
	}
	
	/**
	 * delete
	 */
	@Override
	public void deleteParamTypeById (String id){
		internalParamTypeServiceImpl.delete( id);
	}
	
	
	
	private JQuery<?> buildParamTypeQuery(
			 Map<String, Condition> params){
		String jpql="select a. id as id "
				+ ", a.code as code "
				+ ", a.name as name "
				+ ", a.description as description "
				+ ", a.createId as createId "
				+ ", a.updateId as updateId "
				+ ", a.createTime as createTime "
				+ ", a.updateTime as updateTime "
				+ ", a.deleted as deleted "
				+ ", a.version as version "
				+ " from ParamType a "
				+ " where a.deleted='N' ";
		Condition condition=null;		 
		if((condition=params.get("code"))!=null){
			jpql=jpql+ " and a.code "+condition.getOpe()+" :code";
		}
		if((condition=params.get("name"))!=null){
			jpql=jpql+ " and a.name "+condition.getOpe()+" :name";
		}
		if((condition=params.get("description"))!=null){
			jpql=jpql+ " and a.description "+condition.getOpe()+" :description";
		}
		if((condition=params.get("id"))!=null){
			jpql=jpql+ " and a.id "+condition.getOpe()+" :id";
		}
		if((condition=params.get("createId"))!=null){
			jpql=jpql+ " and a.createId "+condition.getOpe()+" :createId";
		}
		if((condition=params.get("updateId"))!=null){
			jpql=jpql+ " and a.updateId "+condition.getOpe()+" :updateId";
		}
		if((condition=params.get("createTimeStart"))!=null){
			jpql=jpql+ " and a.createTime "+condition.getOpe()+" :createTimeStart";
		}
		if((condition=params.get("createTimeEnd"))!=null){
			jpql=jpql+ " and a.createTime "+condition.getOpe()+" :createTimeEnd";
		}
		if((condition=params.get("createTime"))!=null){
			jpql=jpql+ " and a.createTime "+condition.getOpe()+" :createTime";
		}
		if((condition=params.get("updateTimeStart"))!=null){
			jpql=jpql+ " and a.updateTime "+condition.getOpe()+" :updateTimeStart";
		}
		if((condition=params.get("updateTimeEnd"))!=null){
			jpql=jpql+ " and a.updateTime "+condition.getOpe()+" :updateTimeEnd";
		}
		if((condition=params.get("updateTime"))!=null){
			jpql=jpql+ " and a.updateTime "+condition.getOpe()+" :updateTime";
		}
		if((condition=params.get("deleted"))!=null){
			jpql=jpql+ " and a.deleted "+condition.getOpe()+" :deleted";
		}
		if((condition=params.get("version"))!=null){
			jpql=jpql+ " and a.version "+condition.getOpe()+" :version";
		}
		jpql=jpql+" order by a.updateTime desc";	
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params)); 
	}
	
	/**
	 * get
	 */
	@Override
	public ParamTypeRecord getParamTypeById (String id){
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(id));
		return buildParamTypeQuery( params)
				.model(ParamTypeRecord.class);
	}
	
	
	private Map<String, Condition> getParams(ParamTypeCriteria paramTypeCriteria) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		String code=paramTypeCriteria.getCode();
		if(JStringUtils.isNotNullOrEmpty(code)){
			params.put("code", Condition.likes(code));
		}
		String name=paramTypeCriteria.getName();
		if(JStringUtils.isNotNullOrEmpty(name)){
			params.put("name", Condition.likes(name));
		}
		String description=paramTypeCriteria.getDescription();
		if(JStringUtils.isNotNullOrEmpty(description)){
			params.put("description", Condition.likes(description));
		}
		String id=paramTypeCriteria.getId();
		if(JStringUtils.isNotNullOrEmpty(id)){
			params.put("id", Condition.likes(id));
		}
		String createId=paramTypeCriteria.getCreateId();
		if(JStringUtils.isNotNullOrEmpty(createId)){
			params.put("createId", Condition.likes(createId));
		}
		String updateId=paramTypeCriteria.getUpdateId();
		if(JStringUtils.isNotNullOrEmpty(updateId)){
			params.put("updateId", Condition.likes(updateId));
		}
		String createTimeStart=paramTypeCriteria.getCreateTimeStart();
		if(JStringUtils.isNotNullOrEmpty(createTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTimeStart", Condition.larger(dateParam));
		}
		String createTimeEnd=paramTypeCriteria.getCreateTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(createTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTimeEnd", Condition.smaller(dateParam));
		}
		String createTime=paramTypeCriteria.getCreateTime();
		if(JStringUtils.isNotNullOrEmpty(createTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTime", Condition.equal(dateParam));
		}
		String updateTimeStart=paramTypeCriteria.getUpdateTimeStart();
		if(JStringUtils.isNotNullOrEmpty(updateTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTimeStart", Condition.larger(dateParam));
		}
		String updateTimeEnd=paramTypeCriteria.getUpdateTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(updateTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTimeEnd", Condition.smaller(dateParam));
		}
		String updateTime=paramTypeCriteria.getUpdateTime();
		if(JStringUtils.isNotNullOrEmpty(updateTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTime", Condition.equal(dateParam));
		}
		String deleted=paramTypeCriteria.getDeleted();
		if(JStringUtils.isNotNullOrEmpty(deleted)){
			params.put("deleted", Condition.likes(deleted));
		}
		String version=paramTypeCriteria.getVersion();
		if(JStringUtils.isNotNullOrEmpty(version)){
			params.put("version", Condition.equal(version));
		}
		return params;
	}
	
	
	
	/**
	 * page...
	 */
	@Override
	public JPage<ParamTypeRecord> getParamTypesByPage(ParamTypeCriteria paramTypeCriteria, JSimplePageable simplePageable){
		Map<String, Condition> params = getParams(paramTypeCriteria);
		return buildParamTypeQuery( params)
				.setPageable(simplePageable)
				.modelPage(ParamTypeRecord.class);
	}

	
}
