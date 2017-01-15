package com.youappcorp.project.usermanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.Role;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaRoleRepo")
public interface RoleJPARepo extends JSpringJpaRepository<Role,String>{
	
}
