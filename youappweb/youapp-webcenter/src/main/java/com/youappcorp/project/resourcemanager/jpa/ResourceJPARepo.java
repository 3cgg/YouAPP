package com.youappcorp.project.resourcemanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.resourcemanager.model.Resource;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaResourceRepo")
public interface ResourceJPARepo extends JSpringJpaRepository<Resource,String>{
	
}
