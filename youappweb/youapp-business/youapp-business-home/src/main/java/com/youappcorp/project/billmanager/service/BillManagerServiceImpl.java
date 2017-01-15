/**
 * 
 */
package com.youappcorp.project.billmanager.service;

import me.bunny.app._c._web.core.service.ServiceSupport;
import me.bunny.app._c.jpa.springjpa.query.JJpaDateParam;
import me.bunny.app._c.jpa.springjpa.query.JQuery;
import me.bunny.app._c.jpa.springjpa.query.JCondition.Condition;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JPageable;
import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

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
	public void saveBill( BillRecord billRecord)
			throws BusinessException {
		try{
			internalBillServiceImpl.saveOnly( billRecord.toBill());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateBill( BillRecord billRecord)
			throws BusinessException {
		try{
			Bill dbBill=internalBillServiceImpl.getById( billRecord.getId());
			Bill bill=billRecord.toBill();
			dbBill.setBillTime(bill.getBillTime());
			dbBill.setBillName(bill.getBillName());
			dbBill.setBillType(bill.getBillType());
			dbBill.setDescription(bill.getDescription());
			dbBill.setMoney(bill.getMoney());
			dbBill.setMallName(bill.getMallName());
			internalBillServiceImpl.updateOnly( dbBill);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public BillRecord getBillById( String id) {
		BillRecord billRecord= getBillRecordById( id);
		appendBillInfo( billRecord);
		return billRecord;
	}

	private BillRecord getBillRecordById( String id) {
		return internalBillServiceImpl.singleEntityQuery().conditionDefault()
				.equals("id", id).ready().model(BillRecord.class);
	}
	
	private BillRecord appendBillInfo( BillRecord billRecord){
		UserRecord userRecord=userManagerService.getUserById( billRecord.getUserId());
		billRecord.setUserName(userRecord.getUserName());
		return billRecord;
	}
	
	private void appendBillInfo( List<BillRecord> billRecords){
		for(BillRecord billRecord:billRecords){
			appendBillInfo( billRecord);
		}
	}

	private JQuery<?> buildBillsQuery( Map<String, Condition> params){
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
	public JPage<BillRecord> getBillsByPage(  BillSearchCriteria billSearchCriteria, JPageable pagination) {
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
		JPage<BillRecord> page= buildBillsQuery( params)
				.setPageable(pagination)
				.modelPage(BillRecord.class);
		appendBillInfo( page.getContent());
		return page;
	}

	@Override
	public List<BillRecord> getBillsByUserName( String userName) {
		User user=  userManagerService.getUserByName( userName);
		if(user==null) return null;
		return getBillsByUserId(user.getId());
	}

	@Override
	public List<BillRecord> getBillsByUserId( String userId) {
		List<BillRecord> billRecords= internalBillServiceImpl.singleEntityQuery().conditionDefault()
				.equals("userId", userId).ready().models(BillRecord.class);
		appendBillInfo( billRecords);
		return billRecords;
	}
	
	@Override
	public void deleteBillById( String id) {
		internalBillServiceImpl.delete( id);
	}

	@Override
	public void saveGood( GoodRecord goodRecord) {
		try{
			Good good=goodRecord.toGood();
			internalGoodServiceImpl.saveOnly(good );
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void updateGood( GoodRecord goodRecord) {
		try{
			Good dbGood=internalGoodServiceImpl.getById( goodRecord.getId());
			Good good=goodRecord.toGood();
			dbGood.setDescription(good.getDescription());
			dbGood.setMoney(good.getMoney());
			dbGood.setGoodName(good.getGoodName());
			dbGood.setGoodType(good.getGoodType());
			internalGoodServiceImpl.updateOnly( dbGood);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void deleteGoodById( String id) {
		internalGoodServiceImpl.delete( id);
	}

	@Override
	public GoodRecord getGoodById( String id) {
		return internalGoodServiceImpl.singleEntityQuery().conditionDefault()
				.equals("id", id).ready().model(GoodRecord.class);
	}

	
	private JQuery<?> buildGoodsQuery( Map<String, Condition> params){
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
		if((condition=params.get("billId"))!=null){
			jpql=jpql+" and a.billId "+condition.getOpe()+" :billId";
		}
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
	public JPage<GoodRecord> getGoodsByPage(
			GoodSearchCriteria goodSearchCriteria, JPageable pagination) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		String billId=goodSearchCriteria.getBillId();
		if(JStringUtils.isNotNullOrEmpty(billId)){
			params.put("billId", Condition.equal(billId));
		}
		
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
			params.put("billTimeEnd", Condition.smaller(dateParam));
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
		JPage<GoodRecord> page= buildGoodsQuery( params)
				.setPageable(pagination)
				.modelPage(GoodRecord.class);
		appendGoodInfo( page.getContent());
		return page;
	}

	private GoodRecord appendGoodInfo( GoodRecord goodRecord){
		UserRecord userRecord=userManagerService.getUserById( goodRecord.getUserId());
		goodRecord.setUserName(userRecord.getUserName());
		return goodRecord;
	}
	
	private void appendGoodInfo( List<GoodRecord> goodRecords){
		for(GoodRecord goodRecord:goodRecords){
			appendGoodInfo( goodRecord);
		}
	}

	
	@Override
	public List<GoodRecord> getGoodsByUserName(
			String userName) {
		User user=  userManagerService.getUserByName( userName);
		if(user==null) return null;
		return getGoodsByUserId(user.getId());
	}

	@Override
	public List<GoodRecord> getGoodsByUserId(
			String userId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("userId", Condition.equal(userId));
		List<GoodRecord> goodRecords=buildGoodsQuery( params)
				.models(GoodRecord.class);
		appendGoodInfo( goodRecords);
		return goodRecords;
	}
	
}
