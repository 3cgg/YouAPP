package com.youappcorp.project.menumanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.menumanager.model.MenuGroup;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaMenuGroupRepo")
public interface MenuGroupJPARepo extends JSpringJpaRepository<MenuGroup,String>{
	
}
