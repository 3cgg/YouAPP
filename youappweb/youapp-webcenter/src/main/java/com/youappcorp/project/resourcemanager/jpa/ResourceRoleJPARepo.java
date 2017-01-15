package com.youappcorp.project.resourcemanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.resourcemanager.model.ResourceRole;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaResourceRoleRepo")
public interface ResourceRoleJPARepo extends JSpringJpaRepository<ResourceRole,String>{
	
}
