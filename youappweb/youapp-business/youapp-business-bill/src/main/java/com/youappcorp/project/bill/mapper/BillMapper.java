/**
 * 
 */
package com.youappcorp.project.bill.mapper;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.youappcorp.project.bill.model.Bill;

/**
 * @author Administrator
 *
 */
@Component(value="BillMapper")
@JModelRepo(component="BillMapper",name=Bill.class)
public interface BillMapper extends JMapper<Bill> {
	
	public List<Bill> getBillByUserName(@Param(value="userName")String userName) ;
	
	public List<Bill> getBillsByPage(JPageable pagination);
	
	
}
