package com.youappcorp.project.resourcemanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.resourcemanager.model.ResourceGroup;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaResourceGroupRepo")
public interface ResourceGroupJPARepo extends JSpringJpaRepository<ResourceGroup,String>{
	
}
