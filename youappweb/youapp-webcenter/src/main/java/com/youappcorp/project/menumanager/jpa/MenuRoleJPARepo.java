package com.youappcorp.project.menumanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.menumanager.model.MenuRole;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaMenuRoleRepo")
public interface MenuRoleJPARepo extends JSpringJpaRepository<MenuRole,String>{
	
}
