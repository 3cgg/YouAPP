package com.youappcorp.project.usermanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.User;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaUserRepo")
public interface UserJPARepo extends JSpringJpaRepository<User,String>{
	
}
