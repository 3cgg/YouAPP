package com.youappcorp.project.usermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.RoleGroup;

@Repository(value="jpaRoleGroupRepo")
public interface RoleGroupJPARepo extends JSpringJpaRepository<RoleGroup,String>{
	
}
