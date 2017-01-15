package com.youappcorp.project.billmanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.billmanager.model.Bill;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaBillJPARepo")
public interface BillJPARepo extends JSpringJpaRepository<Bill,String>{
	
}
