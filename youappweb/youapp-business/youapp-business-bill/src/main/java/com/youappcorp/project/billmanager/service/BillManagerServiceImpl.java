/**
 * 
 */
package com.youappcorp.project.billmanager.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.jpa.springjpa.query.JCondition.Condition;
import j.jave.platform.jpa.springjpa.query.JJpaDateParam;
import j.jave.platform.jpa.springjpa.query.JQuery;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.billmanager.model.Bill;
import com.youappcorp.project.billmanager.model.BillRecord;
import com.youappcorp.project.billmanager.model.Good;
import com.youappcorp.project.billmanager.model.GoodRecord;
import com.youappcorp.project.billmanager.vo.BillSearchCriteria;
import com.youappcorp.project.billmanager.vo.GoodSearchCriteria;
import com.youappcorp.project.usermanager.model.User;
import com.youappcorp.project.usermanager.model.UserRecord;
import com.youappcorp.project.usermanager.service.UserManagerService;

/**
 * @author J
 */
@Service(value="billService.transation.jpa")
public class BillManagerServiceImpl extends ServiceSupport implements BillManagerService{

	@Autowired
	private InternalBillServiceImpl internalBillServiceImpl;
	
	@Autowired
	private InternalGoodServiceImpl internalGoodServiceImpl;
	
	
	private UserManagerService userManagerService=
			JServiceHubDelegate.get().getService(this, UserManagerService.class);
	
	@Override
	public void saveBill(ServiceContext serviceContext, BillRecord billRecord)
			throws BusinessException {
		try{
			internalBillServiceImpl.saveOnly(serviceContext, billRecord.toBill());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateBill(ServiceContext serviceContext, BillRecord billRecord)
			throws BusinessException {
		try{
			Bill dbBill=internalBillServiceImpl.getById(serviceContext, billRecord.getId());
			Bill bill=billRecord.toBill();
			dbBill.setBillTime(bill.getBillTime());
			dbBill.setBillName(bill.getBillName());
			dbBill.setBillType(bill.getBillType());
			dbBill.setDescription(bill.getDescription());
			dbBill.setMoney(bill.getMoney());
			dbBill.setMallName(bill.getMallName());
			internalBillServiceImpl.updateOnly(serviceContext, dbBill);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public BillRecord getBillById(ServiceContext serviceContext, String id) {
		BillRecord billRecord= getBillRecordById(serviceContext, id);
		appendBillInfo(serviceContext, billRecord);
		return billRecord;
	}

	private BillRecord getBillRecordById(ServiceContext serviceContext, String id) {
		return internalBillServiceImpl.singleEntityQuery().conditionDefault()
				.equals("id", id).ready().model(BillRecord.class);
	}
	
	private BillRecord appendBillInfo(ServiceContext serviceContext, BillRecord billRecord){
		UserRecord userRecord=userManagerService.getUserById(serviceContext, billRecord.getUserId());
		billRecord.setUserName(userRecord.getUserName());
		return billRecord;
	}
	
	private void appendBillInfo(ServiceContext serviceContext, List<BillRecord> billRecords){
		for(BillRecord billRecord:billRecords){
			appendBillInfo(serviceContext, billRecord);
		}
	}

	private JQuery<?> buildBillsQuery(ServiceContext serviceContext, Map<String, Condition> params){
		String jpql="select a.id as id"
				+ " , a.userId as userId"
				+ " , a.billName as billName"
				+ " , a.billType as billType"
				+ " , a.money as money"
				+ " , a.mallName as mallName"
				+ " , a.billTime as billTime"
				+ " , a.description as description"
				+ " from Bill a "
				+ " where a.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("moneyStart"))!=null){
			jpql=jpql+" and a.money > :moneyStart";
		}
		if((condition=params.get("moneyEnd"))!=null){
			jpql=jpql+" and a.money < :moneyEnd";
		}
		if((condition=params.get("billName"))!=null){
			jpql=jpql+" and a.billName "+condition.getOpe()+" :billName";
		}
		if((condition=params.get("billType"))!=null){
			jpql=jpql+" and a.billType "+condition.getOpe()+" :billType";
		}
		if((condition=params.get("mallName"))!=null){
			jpql=jpql+" and a.mallName "+condition.getOpe()+" :mallName";
		}
		if((condition=params.get("billTimeStart"))!=null){
			jpql=jpql+" and a.billTime > :billTimeStart";
		}
		if((condition=params.get("billTimeEnd"))!=null){
			jpql=jpql+" and a.billTime < :billTimeEnd";
		}
		if((condition=params.get("description"))!=null){
			jpql=jpql+" and a.description "+condition.getOpe()+" :description";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	
	@Override
	public JPage<BillRecord> getBillsByPage(ServiceContext serviceContext,  BillSearchCriteria billSearchCriteria, JPageable pagination) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		String moneyStart=billSearchCriteria.getMoneyStart();
		if(JStringUtils.isNotNullOrEmpty(moneyStart)){
			params.put("moneyStart", Condition.larger(Double.parseDouble(moneyStart)));
		}
		String moneyEnd=billSearchCriteria.getMoneyEnd();
		if(JStringUtils.isNotNullOrEmpty(moneyEnd)){
			params.put("moneyEnd", Condition.smaller(Double.parseDouble(moneyEnd)));
		}
		String billTimeStart=billSearchCriteria.getBillTimeStart();
		if(JStringUtils.isNotNullOrEmpty(billTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(billTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("billTimeStart", Condition.larger(dateParam));
		}
		String billTimeEnd=billSearchCriteria.getBillTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(billTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(billTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("billTimeEnd", Condition.larger(dateParam));
		}
		String billName=billSearchCriteria.getBillName();
		if(JStringUtils.isNotNullOrEmpty(billName)){
			params.put("billName", Condition.likes(billName));
		}
		String billType=billSearchCriteria.getBillType();
		if(JStringUtils.isNotNullOrEmpty(billType)){
			params.put("billType", Condition.likes(billType));
		}
		String mallName=billSearchCriteria.getMallName();
		if(JStringUtils.isNotNullOrEmpty(mallName)){
			params.put("mallName", Condition.likes(mallName));
		}
		String description=billSearchCriteria.getDescription();
		if(JStringUtils.isNotNullOrEmpty(description)){
			params.put("description", Condition.likes(description));
		}
		JPage<BillRecord> page= buildBillsQuery(serviceContext, params)
				.setPageable(pagination)
				.modelPage(BillRecord.class);
		appendBillInfo(serviceContext, page.getContent());
		return page;
	}

	@Override
	public List<BillRecord> getBillsByUserName(ServiceContext serviceContext, String userName) {
		User user=  userManagerService.getUserByName(serviceContext, userName);
		if(user==null) return null;
		return getBillsByUserId(serviceContext,user.getId());
	}

	@Override
	public List<BillRecord> getBillsByUserId(ServiceContext serviceContext, String userId) {
		List<BillRecord> billRecords= internalBillServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().models(BillRecord.class);
		appendBillInfo(serviceContext, billRecords);
		return billRecords;
	}
	
	@Override
	public void deleteBillById(ServiceContext serviceContext, String id) {
		internalBillServiceImpl.delete(serviceContext, id);
	}

	@Override
	public void saveGood(ServiceContext serviceContext, GoodRecord goodRecord) {
		try{
			Good good=goodRecord.toGood();
			internalGoodServiceImpl.saveOnly(serviceContext,good );
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void updateGood(ServiceContext serviceContext, GoodRecord goodRecord) {
		try{
			Good dbGood=internalGoodServiceImpl.getById(serviceContext, goodRecord.getId());
			Good good=goodRecord.toGood();
			dbGood.setDescription(good.getDescription());
			dbGood.setMoney(good.getMoney());
			dbGood.setGoodName(good.getGoodName());
			dbGood.setGoodType(good.getGoodType());
			internalGoodServiceImpl.updateOnly(serviceContext, dbGood);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void deleteGoodById(ServiceContext serviceContext, String id) {
		internalGoodServiceImpl.delete(serviceContext, id);
	}

	@Override
	public GoodRecord getGoodById(ServiceContext serviceContext, String id) {
		return internalGoodServiceImpl.singleEntityQuery().conditionDefault()
				.equals("id", id).ready().model(GoodRecord.class);
	}

	
	private JQuery<?> buildGoodsQuery(ServiceContext serviceContext, Map<String, Condition> params){
		String jpql="select a.id as id"
				+ " , a.billId as billId "
				+ " , a.goodName as goodName"
				+ " , a.goodType as goodType"
				+ " , a.money as money"
				+ " , a.description as description"
				+ ",  b.userId as userId"
				+ " , b.mallName as mallName"
				+ " , b.billTime as billTime"
				+ " from Good a "
				+ " left join Bill b on a.billId=b.id "
				+ " where a.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("moneyStart"))!=null){
			jpql=jpql+" and a.money > :moneyStart";
		}
		if((condition=params.get("moneyEnd"))!=null){
			jpql=jpql+" and a.money < :moneyEnd";
		}
		if((condition=params.get("goodName"))!=null){
			jpql=jpql+" and a.goodName "+condition.getOpe()+" :goodName";
		}
		if((condition=params.get("goodType"))!=null){
			jpql=jpql+" and a.goodType "+condition.getOpe()+" :goodType";
		}
		if((condition=params.get("description"))!=null){
			jpql=jpql+" and a.description "+condition.getOpe()+" :description";
		}
		
		if((condition=params.get("mallName"))!=null){
			jpql=jpql+" and b.mallName "+condition.getOpe()+" :mallName";
		}
		if((condition=params.get("billTimeStart"))!=null){
			jpql=jpql+" and b.billTime > :billTimeStart";
		}
		if((condition=params.get("billTimeEnd"))!=null){
			jpql=jpql+" and b.billTime < :billTimeEnd";
		}
		
		if((condition=params.get("billName"))!=null){
			jpql=jpql+" and b.billName "+condition.getOpe()+" :billName";
		}
		if((condition=params.get("billType"))!=null){
			jpql=jpql+" and b.billType "+condition.getOpe()+" :billType";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	
	@Override
	public JPage<GoodRecord> getGoodsByPage(ServiceContext serviceContext,
			GoodSearchCriteria goodSearchCriteria, JPageable pagination) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		String moneyStart=goodSearchCriteria.getMoneyStart();
		if(JStringUtils.isNotNullOrEmpty(moneyStart)){
			params.put("moneyStart", Condition.larger(Double.parseDouble(moneyStart)));
		}
		String moneyEnd=goodSearchCriteria.getMoneyEnd();
		if(JStringUtils.isNotNullOrEmpty(moneyEnd)){
			params.put("moneyEnd", Condition.smaller(Double.parseDouble(moneyEnd)));
		}
		String billTimeStart=goodSearchCriteria.getBillTimeStart();
		if(JStringUtils.isNotNullOrEmpty(billTimeStart)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(billTimeStart));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("billTimeStart", Condition.larger(dateParam));
		}
		String billTimeEnd=goodSearchCriteria.getBillTimeEnd();
		if(JStringUtils.isNotNullOrEmpty(billTimeEnd)){
			JJpaDateParam dateParam=new JJpaDateParam();
			dateParam.setDate(JDateUtils.parseDate(billTimeEnd));
			dateParam.setTemporalType(TemporalType.TIMESTAMP);
			params.put("billTimeEnd", Condition.larger(dateParam));
		}
		String goodName=goodSearchCriteria.getGoodName();
		if(JStringUtils.isNotNullOrEmpty(goodName)){
			params.put("goodName", Condition.likes(goodName));
		}
		String goodType=goodSearchCriteria.getGoodType();
		if(JStringUtils.isNotNullOrEmpty(goodType)){
			params.put("goodType", Condition.likes(goodType));
		}
		String mallName=goodSearchCriteria.getMallName();
		if(JStringUtils.isNotNullOrEmpty(mallName)){
			params.put("mallName", Condition.likes(mallName));
		}
		String description=goodSearchCriteria.getDescription();
		if(JStringUtils.isNotNullOrEmpty(description)){
			params.put("description", Condition.likes(description));
		}
		String billName=goodSearchCriteria.getBillName();
		if(JStringUtils.isNotNullOrEmpty(billName)){
			params.put("billName", Condition.likes(billName));
		}
		String billType=goodSearchCriteria.getBillType();
		if(JStringUtils.isNotNullOrEmpty(billType)){
			params.put("billType", Condition.likes(billType));
		}
		JPage<GoodRecord> page= buildGoodsQuery(serviceContext, params)
				.setPageable(pagination)
				.modelPage(GoodRecord.class);
		appendGoodInfo(serviceContext, page.getContent());
		return page;
	}

	private GoodRecord appendGoodInfo(ServiceContext serviceContext, GoodRecord goodRecord){
		UserRecord userRecord=userManagerService.getUserById(serviceContext, goodRecord.getUserId());
		goodRecord.setUserName(userRecord.getUserName());
		return goodRecord;
	}
	
	private void appendGoodInfo(ServiceContext serviceContext, List<GoodRecord> goodRecords){
		for(GoodRecord goodRecord:goodRecords){
			appendGoodInfo(serviceContext, goodRecord);
		}
	}

	
	@Override
	public List<GoodRecord> getGoodsByUserName(ServiceContext serviceContext,
			String userName) {
		User user=  userManagerService.getUserByName(serviceContext, userName);
		if(user==null) return null;
		return getGoodsByUserId(serviceContext,user.getId());
	}

	@Override
	public List<GoodRecord> getGoodsByUserId(ServiceContext serviceContext,
			String userId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("userId", Condition.equal(userId));
		List<GoodRecord> goodRecords=buildGoodsQuery(serviceContext, params)
				.models(GoodRecord.class);
		appendGoodInfo(serviceContext, goodRecords);
		return goodRecords;
	}
	
}
