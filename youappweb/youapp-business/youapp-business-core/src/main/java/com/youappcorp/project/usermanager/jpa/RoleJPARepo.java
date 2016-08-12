package com.youappcorp.project.usermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.Role;

@Repository(value="jpaRoleRepo")
public interface RoleJPARepo extends JSpringJpaRepository<Role,String>{
	
}
