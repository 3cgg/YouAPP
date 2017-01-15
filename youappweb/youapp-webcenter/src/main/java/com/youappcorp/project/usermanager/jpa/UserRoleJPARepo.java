package com.youappcorp.project.usermanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.UserRole;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaUserRoleRepo")
public interface UserRoleJPARepo extends JSpringJpaRepository<UserRole,String>{
	
}
