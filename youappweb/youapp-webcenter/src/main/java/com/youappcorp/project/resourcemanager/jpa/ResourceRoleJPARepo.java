package com.youappcorp.project.resourcemanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.resourcemanager.model.ResourceRole;

@Repository(value="jpaResourceRoleRepo")
public interface ResourceRoleJPARepo extends JSpringJpaRepository<ResourceRole,String>{
	
}
