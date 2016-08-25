package com.youappcorp.project.menumanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.menumanager.model.Menu;

@Repository(value="jpaMenuRepo")
public interface MenuJPARepo extends JSpringJpaRepository<Menu,String>{
	
}
