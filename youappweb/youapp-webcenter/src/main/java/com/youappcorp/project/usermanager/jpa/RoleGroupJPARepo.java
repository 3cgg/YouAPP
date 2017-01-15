package com.youappcorp.project.usermanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.RoleGroup;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaRoleGroupRepo")
public interface RoleGroupJPARepo extends JSpringJpaRepository<RoleGroup,String>{
	
}
