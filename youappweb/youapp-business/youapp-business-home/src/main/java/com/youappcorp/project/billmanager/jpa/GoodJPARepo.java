package com.youappcorp.project.billmanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.billmanager.model.Good;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaGoodJPARepo")
public interface GoodJPARepo extends JSpringJpaRepository<Good,String>{
	
}
