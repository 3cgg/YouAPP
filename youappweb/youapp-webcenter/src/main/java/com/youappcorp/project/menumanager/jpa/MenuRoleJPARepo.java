package com.youappcorp.project.menumanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.menumanager.model.MenuRole;

@Repository(value="jpaMenuRoleRepo")
public interface MenuRoleJPARepo extends JSpringJpaRepository<MenuRole,String>{
	
}
