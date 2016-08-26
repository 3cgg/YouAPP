package com.youappcorp.project.menumanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.menumanager.model.MenuGroup;

@Repository(value="jpaMenuGroupRepo")
public interface MenuGroupJPARepo extends JSpringJpaRepository<MenuGroup,String>{
	
}
