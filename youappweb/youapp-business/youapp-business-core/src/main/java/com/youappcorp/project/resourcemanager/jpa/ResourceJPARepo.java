package com.youappcorp.project.resourcemanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.resourcemanager.model.Resource;

@Repository(value="jpaResourceRepo")
public interface ResourceJPARepo extends JSpringJpaRepository<Resource,String>{
	
}
