package com.youappcorp.project.alertmanager.service;

import j.jave.platform.webcomp.core.service.ServiceSupport;
import me.bunny.app._c.jpa.springjpa.query.JJpaDateParam;
import me.bunny.app._c.jpa.springjpa.query.JQuery;
import me.bunny.app._c.jpa.springjpa.query.JCondition.Condition;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel._c.utils.JStringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.alertmanager.model.AlertItem;
import com.youappcorp.project.alertmanager.model.AlertItemRecord;
import com.youappcorp.project.alertmanager.vo.AlertItemCriteria;
import com.youappcorp.project.alertmanager.service.InternalAlertItemServiceImpl;
import com.youappcorp.project.alertmanager.model.AlertRecord;
import com.youappcorp.project.alertmanager.model.AlertRecordRecord;
import com.youappcorp.project.alertmanager.vo.AlertRecordCriteria;
import com.youappcorp.project.alertmanager.service.InternalAlertRecordServiceImpl;

import com.youappcorp.project.alertmanager.service.AlertManagerService;

@Service(value="AlertManagerServiceImpl.transation.jpa")
public class AlertManagerServiceImpl extends ServiceSupport implements AlertManagerService {

	@Autowired
	private InternalAlertItemServiceImpl internalAlertItemServiceImpl;

	@Autowired
	private InternalAlertRecordServiceImpl internalAlertRecordServiceImpl;


	/**
	 * save
	 */
	@Override
	public void saveAlertItem (AlertItemRecord alertItemRecord){
		try{
			AlertItem alertItem=alertItemRecord.toAlertItem();
			internalAlertItemServiceImpl.saveOnly( alertItem);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * update
	 */
	@Override
	public void updateAlertItem (AlertItemRecord alertItemRecord){
		try{
			AlertItem alertItem=alertItemRecord.toAlertItem();
			AlertItem dbAlertItem=internalAlertItemServiceImpl.getById( alertItem.getId());
			
			dbAlertItem.setCode(alertItem.getCode());
			dbAlertItem.setName(alertItem.getName());
			dbAlertItem.setStatus(alertItem.getStatus());
			dbAlertItem.setMeta(alertItem.getMeta());
			dbAlertItem.setDescription(alertItem.getDescription());
			internalAlertItemServiceImpl.updateOnly( dbAlertItem);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * delete
	 */
	@Override
	public void deleteAlertItem (AlertItemRecord alertItemRecord){
		internalAlertItemServiceImpl.delete(alertItemRecord.toAlertItem().getId());
	}
	
	/**
	 * delete
	 */
	@Override
	public void deleteAlertItemById (String id){
		internalAlertItemServiceImpl.delete( id);
	}
	
	
	
	private JQuery<?> buildAlertItemQuery(
			 Map<String, Condition> params){
		String jpql="select a. id as id "
				+ ", a.code as code "
				+ ", a.name as name "
				+ ", a.status as status "
				+ ", a.meta as meta "
				+ ", a.description as description "
				+ ", a.createId as createId "
				+ ", a.updateId as updateId "
				+ ", a.createTime as createTime "
				+ ", a.updateTime as updateTime "
				+ ", a.deleted as deleted "
				+ ", a.version as version "
				+ " from AlertItem a "
				+ " where a.deleted='N' ";
		Condition condition=null;		 
		if((condition=params.get("code"))!=null){
			jpql=jpql+ " and a.code "+condition.getOpe()+" :code";
		}
		if((condition=params.get("name"))!=null){
			jpql=jpql+ " and a.name "+condition.getOpe()+" :name";
		}
		if((condition=params.get("status"))!=null){
			jpql=jpql+ " and a.status "+condition.getOpe()+" :status";
		}
		if((condition=params.get("meta"))!=null){
			jpql=jpql+ " and a.meta "+condition.getOpe()+" :meta";
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
	public AlertItemRecord getAlertItemById (String id){
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(id));
		return buildAlertItemQuery( params)
				.model(AlertItemRecord.class);
	}
	
	
	private Map<String, Condition> getParams(AlertItemCriteria alertItemCriteria) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		String code=alertItemCriteria.getCode();
		if(JStringUtils.isNotNullOrEmpty(code)){
			params.put("code", Condition.likes(code));
		}
		String name=alertItemCriteria.getName();
		if(JStringUtils.isNotNullOrEmpty(name)){
			params.put("name", Condition.likes(name));
		}
		String status=alertItemCriteria.getStatus();
		if(JStringUtils.isNotNullOrEmpty(status)){
			params.put("status", Condition.likes(status));
		}
		String meta=alertItemCriteria.getMeta();
		if(JStringUtils.isNotNullOrEmpty(meta)){
			params.put("meta", Condition.likes(meta));
		}
		String description=alertItemCriteria.getDescription();
		if(JStringUtils.isNotNullOrEmpty(description)){
			params.put("description", Condition.likes(description));
		}
		String id=alertItemCriteria.getId();
		if(JStringUtils.isNotNullOrEmpty(id)){
			params.put("id", Condition.likes(id));
		}
		String createId=alertItemCriteria.getCreateId();
		if(JStringUtils.isNotNullOrEmpty(createId)){
			params.put("createId", Condition.likes(createId));
		}
		String updateId=alertItemCriteria.getUpdateId();
		if(JStringUtils.isNotNullOrEmpty(updateId)){
			params.put("updateId", Condition.likes(updateId));
		}
		String createTimeStart=alertItemCriteria.getCreateTimeStart();
		if(JStringUtils.isNotNullOrEmpty(createTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTimeStart", Condition.larger(dateParam));
		}
		String createTimeEnd=alertItemCriteria.getCreateTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(createTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTimeEnd", Condition.smaller(dateParam));
		}
		String createTime=alertItemCriteria.getCreateTime();
		if(JStringUtils.isNotNullOrEmpty(createTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTime", Condition.equal(dateParam));
		}
		String updateTimeStart=alertItemCriteria.getUpdateTimeStart();
		if(JStringUtils.isNotNullOrEmpty(updateTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTimeStart", Condition.larger(dateParam));
		}
		String updateTimeEnd=alertItemCriteria.getUpdateTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(updateTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTimeEnd", Condition.smaller(dateParam));
		}
		String updateTime=alertItemCriteria.getUpdateTime();
		if(JStringUtils.isNotNullOrEmpty(updateTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTime", Condition.equal(dateParam));
		}
		String deleted=alertItemCriteria.getDeleted();
		if(JStringUtils.isNotNullOrEmpty(deleted)){
			params.put("deleted", Condition.likes(deleted));
		}
		String version=alertItemCriteria.getVersion();
		if(JStringUtils.isNotNullOrEmpty(version)){
			params.put("version", Condition.equal(version));
		}
		return params;
	}
	
	
	
	/**
	 * page...
	 */
	@Override
	public JPage<AlertItemRecord> getAlertItemsByPage(AlertItemCriteria alertItemCriteria, JSimplePageable simplePageable){
		Map<String, Condition> params = getParams(alertItemCriteria);
		return buildAlertItemQuery( params)
				.setPageable(simplePageable)
				.modelPage(AlertItemRecord.class);
	}

	/**
	 * save
	 */
	@Override
	public void saveAlertRecord (AlertRecordRecord alertRecordRecord){
		try{
			AlertRecord alertRecord=alertRecordRecord.toAlertRecord();
			internalAlertRecordServiceImpl.saveOnly( alertRecord);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * update
	 */
	@Override
	public void updateAlertRecord (AlertRecordRecord alertRecordRecord){
		try{
			AlertRecord alertRecord=alertRecordRecord.toAlertRecord();
			AlertRecord dbAlertRecord=internalAlertRecordServiceImpl.getById( alertRecord.getId());
			
			dbAlertRecord.setAlertItemId(alertRecord.getAlertItemId());
			dbAlertRecord.setRecordTime(alertRecord.getRecordTime());
			dbAlertRecord.setData(alertRecord.getData());
			dbAlertRecord.setDescription(alertRecord.getDescription());
			dbAlertRecord.setPrimary(alertRecord.getPrimary());
			internalAlertRecordServiceImpl.updateOnly( dbAlertRecord);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	/**
	 * delete
	 */
	@Override
	public void deleteAlertRecord (AlertRecordRecord alertRecordRecord){
		internalAlertRecordServiceImpl.delete(alertRecordRecord.toAlertRecord().getId());
	}
	
	/**
	 * delete
	 */
	@Override
	public void deleteAlertRecordById (String id){
		internalAlertRecordServiceImpl.delete( id);
	}
	
	
	
	private JQuery<?> buildAlertRecordQuery(
			 Map<String, Condition> params){
		String jpql="select a. id as id "
				+ ", a.alertItemId as alertItemId "
				+ ", a.recordTime as recordTime "
				+ ", a.data as data "
				+ ", a.description as description "
				+ ", a.primary as primary "
				+ ", a.createId as createId "
				+ ", a.updateId as updateId "
				+ ", a.createTime as createTime "
				+ ", a.updateTime as updateTime "
				+ ", a.deleted as deleted "
				+ ", a.version as version "
				+ " from AlertRecord a "
				+ " where a.deleted='N' ";
		Condition condition=null;		 
		if((condition=params.get("alertItemId"))!=null){
			jpql=jpql+ " and a.alertItemId "+condition.getOpe()+" :alertItemId";
		}
		if((condition=params.get("recordTimeStart"))!=null){
			jpql=jpql+ " and a.recordTime "+condition.getOpe()+" :recordTimeStart";
		}
		if((condition=params.get("recordTimeEnd"))!=null){
			jpql=jpql+ " and a.recordTime "+condition.getOpe()+" :recordTimeEnd";
		}
		if((condition=params.get("recordTime"))!=null){
			jpql=jpql+ " and a.recordTime "+condition.getOpe()+" :recordTime";
		}
		if((condition=params.get("data"))!=null){
			jpql=jpql+ " and a.data "+condition.getOpe()+" :data";
		}
		if((condition=params.get("description"))!=null){
			jpql=jpql+ " and a.description "+condition.getOpe()+" :description";
		}
		if((condition=params.get("primary"))!=null){
			jpql=jpql+ " and a.primary "+condition.getOpe()+" :primary";
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
	public AlertRecordRecord getAlertRecordById (String id){
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(id));
		return buildAlertRecordQuery( params)
				.model(AlertRecordRecord.class);
	}
	
	
	private Map<String, Condition> getParams(AlertRecordCriteria alertRecordCriteria) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		String alertItemId=alertRecordCriteria.getAlertItemId();
		if(JStringUtils.isNotNullOrEmpty(alertItemId)){
			params.put("alertItemId", Condition.likes(alertItemId));
		}
		String recordTimeStart=alertRecordCriteria.getRecordTimeStart();
		if(JStringUtils.isNotNullOrEmpty(recordTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(recordTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("recordTimeStart", Condition.larger(dateParam));
		}
		String recordTimeEnd=alertRecordCriteria.getRecordTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(recordTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(recordTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("recordTimeEnd", Condition.smaller(dateParam));
		}
		String recordTime=alertRecordCriteria.getRecordTime();
		if(JStringUtils.isNotNullOrEmpty(recordTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(recordTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("recordTime", Condition.equal(dateParam));
		}
		String data=alertRecordCriteria.getData();
		if(JStringUtils.isNotNullOrEmpty(data)){
			params.put("data", Condition.likes(data));
		}
		String description=alertRecordCriteria.getDescription();
		if(JStringUtils.isNotNullOrEmpty(description)){
			params.put("description", Condition.likes(description));
		}
		String primary=alertRecordCriteria.getPrimary();
		if(JStringUtils.isNotNullOrEmpty(primary)){
			params.put("primary", Condition.likes(primary));
		}
		String id=alertRecordCriteria.getId();
		if(JStringUtils.isNotNullOrEmpty(id)){
			params.put("id", Condition.likes(id));
		}
		String createId=alertRecordCriteria.getCreateId();
		if(JStringUtils.isNotNullOrEmpty(createId)){
			params.put("createId", Condition.likes(createId));
		}
		String updateId=alertRecordCriteria.getUpdateId();
		if(JStringUtils.isNotNullOrEmpty(updateId)){
			params.put("updateId", Condition.likes(updateId));
		}
		String createTimeStart=alertRecordCriteria.getCreateTimeStart();
		if(JStringUtils.isNotNullOrEmpty(createTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTimeStart", Condition.larger(dateParam));
		}
		String createTimeEnd=alertRecordCriteria.getCreateTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(createTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTimeEnd", Condition.smaller(dateParam));
		}
		String createTime=alertRecordCriteria.getCreateTime();
		if(JStringUtils.isNotNullOrEmpty(createTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(createTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("createTime", Condition.equal(dateParam));
		}
		String updateTimeStart=alertRecordCriteria.getUpdateTimeStart();
		if(JStringUtils.isNotNullOrEmpty(updateTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTimeStart", Condition.larger(dateParam));
		}
		String updateTimeEnd=alertRecordCriteria.getUpdateTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(updateTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTimeEnd", Condition.smaller(dateParam));
		}
		String updateTime=alertRecordCriteria.getUpdateTime();
		if(JStringUtils.isNotNullOrEmpty(updateTime)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(updateTime));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("updateTime", Condition.equal(dateParam));
		}
		String deleted=alertRecordCriteria.getDeleted();
		if(JStringUtils.isNotNullOrEmpty(deleted)){
			params.put("deleted", Condition.likes(deleted));
		}
		String version=alertRecordCriteria.getVersion();
		if(JStringUtils.isNotNullOrEmpty(version)){
			params.put("version", Condition.equal(version));
		}
		return params;
	}
	
	
	
	/**
	 * page...
	 */
	@Override
	public JPage<AlertRecordRecord> getAlertRecordsByPage(AlertRecordCriteria alertRecordCriteria, JSimplePageable simplePageable){
		Map<String, Condition> params = getParams(alertRecordCriteria);
		return buildAlertRecordQuery( params)
				.setPageable(simplePageable)
				.modelPage(AlertRecordRecord.class);
	}

	
}
