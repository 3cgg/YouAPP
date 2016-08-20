package com.youappcorp.project.billmanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.billmanager.model.Bill;

@Repository(value="jpaBillJPARepo")
public interface BillJPARepo extends JSpringJpaRepository<Bill,String>{
	
}
