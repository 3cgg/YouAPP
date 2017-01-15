package com.youappcorp.project.usermanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.Group;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaGroupRepo")
public interface GroupJPARepo extends JSpringJpaRepository<Group,String>{
	
}
