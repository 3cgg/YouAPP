package com.youappcorp.project.menumanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.menumanager.model.Menu;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaMenuRepo")
public interface MenuJPARepo extends JSpringJpaRepository<Menu,String>{
	
}
