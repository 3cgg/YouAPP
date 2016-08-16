package com.youappcorp.project.resourcemanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.resourcemanager.model.ResourceGroup;

@Repository(value="jpaResourceGroupRepo")
public interface ResourceGroupJPARepo extends JSpringJpaRepository<ResourceGroup,String>{
	
}
