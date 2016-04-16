package com.youappcorp.project.bill.repo;

import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.youappcorp.project.bill.model.Bill;

@Repository(value="jpaBillJPARepo")
public interface BillJPARepo extends JSpringJpaRepository<Bill, String>{

	@Query(value="from Bill p where p.userId=:userId and p.deleted='N' ")
	public List<Bill> getBillByUserId(@Param(value="userId")String userId);
	
	
}
