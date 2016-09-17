package ${classPackage};

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
<#list models as model>
import ${model.modelModel.className};
import ${model.modelRecordModel.className};
import ${model.criteriaModel.className};
import ${model.internalServiceModel.className};
</#list>

import ${interfaceClassName};

public class ${simpleClassName} extends ServiceSupport implements ${simpleInterfaceClassName} {

<#list models as model>
	@Autowired
	private ${model.internalServiceModel.simpleClassName} ${model.internalServiceModel.variableName};

</#list>

<#list models as model>
	/**
	 * save
	 */
	@Override
	public void ${model.serviceModel.saveMethodName} (${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName}){
		try{
			${model.modelModel.simpleClassName} ${model.modelModel.variableName}=${model.modelRecordModel.variableName}.to${model.modelModel.simpleClassName}();
			${model.internalServiceModel.variableName}.saveOnly( ${model.modelModel.variableName});
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * update
	 */
	@Override
	public void ${model.serviceModel.updateMethodName} (${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName}){
		try{
			${model.modelModel.simpleClassName} ${model.modelModel.variableName}=${model.modelRecordModel.variableName}.to${model.modelModel.simpleClassName}();
			${model.modelModel.simpleClassName} db${model.modelModel.simpleClassName}=${model.internalServiceModel.variableName}.getById( ${model.modelModel.variableName}.getId());
			
			<#list model.modelModel.modelFields as modelField>
			<#if  modelField.property!="id"
					&&modelField.property!="createId"
					&&modelField.property!="createTime"
					&&modelField.property!="updateId"
					&&modelField.property!="updateTime"
					&&modelField.property!="deleted"
					&&modelField.property!="version"
			 >
			db${model.modelModel.simpleClassName}.${modelField.setterMethodName}(${model.modelModel.variableName}.${modelField.getterMethodName}());
			</#if>		
			</#list>
			${model.internalServiceModel.variableName}.updateOnly( db${model.modelModel.simpleClassName});
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * delete
	 */
	@Override
	public void ${model.serviceModel.deleteMethodName} (${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName}){
		${model.internalServiceModel.variableName}.delete(${model.modelRecordModel.variableName}.to${model.modelModel.simpleClassName}().getId());
	}
	
	/**
	 * delete
	 */
	@Override
	public void ${model.serviceModel.deleteByIdMethodName} (String id){
		${model.internalServiceModel.variableName}.delete( id);
	}
	
	
	
	private JQuery<?> build${model.modelModel.simpleClassName}Query(
			 Map<String, Condition> params){
		String jpql="select a. id as id "
				<#list model.modelModel.modelFields as modelField>
				<#if  modelField.property!="id">
				+ ", a.${modelField.property} as ${modelField.property} "
				</#if>
				</#list>
				+ " from ${model.modelModel.simpleClassName} a "
				+ " where a.deleted='N' ";
		Condition condition=null;		 
		<#list model.criteriaModel.modelFields as modelField>
		if((condition=params.get("${modelField.property}"))!=null){
			jpql=jpql+ " and a.${modelField.source.property} "+condition.getOpe()+" :${modelField.property}";
		}
		</#list>
		jpql=jpql+" order by a.updateTime desc";	
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params)); 
	}
	
	/**
	 * get
	 */
	@Override
	public ${model.modelRecordModel.simpleClassName} ${model.serviceModel.getMethodName} (String id){
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(id));
		return build${model.modelModel.simpleClassName}Query( params)
				.model(${model.modelRecordModel.simpleClassName}.class);
	}
	
	
	private Map<String, Condition> getParams(${model.criteriaModel.simpleClassName} ${model.criteriaModel.variableName}) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		<#list model.criteriaModel.modelFields as modelField>
		<#if modelField.fieldType=="string">
		String ${modelField.property}=${model.criteriaModel.variableName}.${modelField.getterMethodName}();
		if(JStringUtils.isNotNullOrEmpty(${modelField.property})){
			params.put("${modelField.property}", Condition.likes(${modelField.property}));
		}
		</#if>
		<#if modelField.fieldType=="numeric">
		String ${modelField.property}=${model.criteriaModel.variableName}.${modelField.getterMethodName}();
		if(JStringUtils.isNotNullOrEmpty(${modelField.property})){
			params.put("${modelField.property}", Condition.equal(${modelField.property}));
		}
		</#if>
		<#if modelField.fieldType=="date">
		String ${modelField.property}=${model.criteriaModel.variableName}.${modelField.getterMethodName}();
		if(JStringUtils.isNotNullOrEmpty(${modelField.property})){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(${modelField.property}));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			<#if modelField.property?ends_with("Start")>
			params.put("${modelField.property}", Condition.larger(dateParam));
			<#elseif modelField.property?ends_with("End")>
			params.put("${modelField.property}", Condition.smaller(dateParam));
			<#else>
			params.put("${modelField.property}", Condition.equal(dateParam));
			</#if>
		}
		</#if>
		</#list>
		return params;
	}
	
	
	
	/**
	 * page...
	 */
	@Override
	public JPage<${model.modelRecordModel.simpleClassName}> ${model.serviceModel.pageMethodName}(${model.criteriaModel.simpleClassName} ${model.criteriaModel.variableName}, JSimplePageable simplePageable){
		Map<String, Condition> params = getParams(${model.criteriaModel.variableName});
		return build${model.modelModel.simpleClassName}Query( params)
				.setPageable(simplePageable)
				.modelPage(${model.modelRecordModel.simpleClassName}.class);
	}

</#list>
	
}
