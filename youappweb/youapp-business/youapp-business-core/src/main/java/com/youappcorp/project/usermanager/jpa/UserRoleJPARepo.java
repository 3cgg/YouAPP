package com.youappcorp.project.usermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.UserRole;

@Repository(value="jpaUserRoleRepo")
public interface UserRoleJPARepo extends JSpringJpaRepository<UserRole,String>{
	
}
