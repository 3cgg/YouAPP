package com.youappcorp.project.usermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.Group;

@Repository(value="jpaGroupRepo")
public interface GroupJPARepo extends JSpringJpaRepository<Group,String>{
	
}
