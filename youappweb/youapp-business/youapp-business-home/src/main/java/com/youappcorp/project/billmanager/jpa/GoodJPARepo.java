package com.youappcorp.project.billmanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.billmanager.model.Good;

@Repository(value="jpaGoodJPARepo")
public interface GoodJPARepo extends JSpringJpaRepository<Good,String>{
	
}
